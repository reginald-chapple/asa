package com.asa.web.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asa.web.dto.rag.UploadDto;
import com.asa.web.service.EmbeddingService;
import com.asa.web.service.FileValidationService;
import com.asa.web.service.FilterService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final FileValidationService fileValidationService;
    private final EmbeddingService embeddingService;
    private final FilterService filterService;

    @GetMapping
    @RolesAllowed("ADMIN")
    public String index() {
        return "home/index";
    }

    @GetMapping("upload")
    @RolesAllowed("ADMIN")
    public String showUploadForm(Model model) {
        model.addAttribute("upload", new UploadDto());
        model.addAttribute("filters", filterService.getAllFilters());
        return "home/upload";
    }
    
    @PostMapping("upload")
    @RolesAllowed("ADMIN")
    public String handleFileUpload(@ModelAttribute("upload") @Valid UploadDto dto, Model model, RedirectAttributes redirectAttributes) {
        
        if (dto.getFile().isEmpty()) {
            model.addAttribute("filters", filterService.getAllFilters());
            redirectAttributes.addFlashAttribute("error", "Please select a file to upload.");
            return "redirect:/upload";
        }

        try {
            // Validate file type
            if (!fileValidationService.isValidFileType(dto.getFile())) {
                String detectedType = fileValidationService.getDetectedMimeType(dto.getFile());
                model.addAttribute("filters", filterService.getAllFilters());
                redirectAttributes.addFlashAttribute("error", 
                    String.format("Invalid file type. Detected: %s. Only PDF and CSV files are allowed.", detectedType));
                return "redirect:/upload";
            }

            // Create resource from file
            Resource resource = embeddingService.createResource(dto.getFile());

            // Generate embeddings
            embeddingService.generate(resource, dto.getFilters().stream()
                .map(id -> filterService.getFilterById(id))
                .collect(Collectors.toSet()));

            redirectAttributes.addFlashAttribute("success", 
                String.format("File '%s' uploaded and processed successfully! Embeddings have been generated.", 
                dto.getFile().getOriginalFilename()));

            log.info("Successfully processed file: {} (size: {} bytes)", 
                dto.getFile().getOriginalFilename(), dto.getFile().getSize());

        } catch (IOException e) {
            log.error("IO error processing file: {}", dto.getFile().getOriginalFilename(), e);
            model.addAttribute("filters", filterService.getAllFilters());
            redirectAttributes.addFlashAttribute("error", 
                "Error reading file: " + e.getMessage());
            return "redirect:/upload";
        } catch (Exception e) {
            log.error("Error processing file: {}", dto.getFile().getOriginalFilename(), e);
            model.addAttribute("filters", filterService.getAllFilters());
            redirectAttributes.addFlashAttribute("error", 
                "Error processing file: " + e.getMessage());
            return "redirect:/upload";
        }

        return "redirect:/upload";
    }
}
