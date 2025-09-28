package com.asa.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asa.web.dto.prediction.AskQuestionFormDto;
import com.asa.web.service.PredictionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("predictions", predictionService.getAllPredictions());
        return "predictions/index";
    }

    @GetMapping("/ask-question")
    public String askQuestionForm(Model model) {
        model.addAttribute("question", new AskQuestionFormDto());
        return "predictions/question";
    }

    @PostMapping("/ask-question")
    public String askQuestion(@ModelAttribute("question") @Valid AskQuestionFormDto dto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", result.getAllErrors());
            return "predictions/question";
        }

        redirectAttributes.addFlashAttribute("success", "Question submitted successfully!");

        predictionService.createPrediction(dto.getQuestion());
        return "redirect:/predictions";
    }

    @GetMapping("/{id}")
    public String viewPrediction(@PathVariable Long id, Model model) {
        model.addAttribute("prediction", predictionService.getPredictionById(id));
        return "predictions/view";
    }
}
