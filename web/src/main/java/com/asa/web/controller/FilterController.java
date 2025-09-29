package com.asa.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asa.web.dto.filter.FilterFormDto;
import com.asa.web.model.Filter;
import com.asa.web.service.FilterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/filters")
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("filters", filterService.getAllFilters());
        return "filters/index";
    }

    @GetMapping("/add")
    public String addFilterForm(Model model) {
        model.addAttribute("filter", new FilterFormDto());
        return "filters/add";
    }

    @PostMapping("/add")
    public String addFilter(@ModelAttribute("filter") @Valid Filter filter, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", result.getAllErrors());
            return "filters/add";
        }
        filterService.saveFilter(filter);
        redirectAttributes.addFlashAttribute("success", "Filter added successfully!");
        return "redirect:/filters";
    }

}
