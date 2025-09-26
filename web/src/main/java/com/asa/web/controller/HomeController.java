package com.asa.web.controller;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asa.web.service.EmbeddingService;
import com.asa.web.service.FileValidationService;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final FileValidationService fileValidationService;
    private final EmbeddingService embeddingService;

    @GetMapping
    @RolesAllowed("ADMIN")
    public String index() {
        return "home/index";
    }

    @GetMapping("upload")
    @RolesAllowed("ADMIN")
    public String showUploadForm(Model model) {
        return "home/upload";
    }
    
    @PostMapping("upload")
    @RolesAllowed("ADMIN")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, 
                                RedirectAttributes redirectAttributes) {
        
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select a file to upload.");
            return "redirect:/upload";
        }

        try {
            // Validate file type
            if (!fileValidationService.isValidFileType(file)) {
                String detectedType = fileValidationService.getDetectedMimeType(file);
                redirectAttributes.addFlashAttribute("error", 
                    String.format("Invalid file type. Detected: %s. Only PDF and CSV files are allowed.", detectedType));
                return "redirect:/upload";
            }

            // Create resource from file
            Resource resource = embeddingService.createResource(file);

            // Generate embeddings
            embeddingService.generate(resource);

            redirectAttributes.addFlashAttribute("success", 
                String.format("File '%s' uploaded and processed successfully! Embeddings have been generated.", 
                file.getOriginalFilename()));

            log.info("Successfully processed file: {} (size: {} bytes)", 
                file.getOriginalFilename(), file.getSize());

        } catch (IOException e) {
            log.error("IO error processing file: {}", file.getOriginalFilename(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error reading file: " + e.getMessage());
            return "redirect:/upload";
        } catch (Exception e) {
            log.error("Error processing file: {}", file.getOriginalFilename(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Error processing file: " + e.getMessage());
            return "redirect:/upload";
        }

        return "redirect:/upload";
    }
}
