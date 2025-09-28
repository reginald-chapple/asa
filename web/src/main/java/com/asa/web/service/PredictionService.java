package com.asa.web.service;

import java.util.List;
import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.asa.web.dto.prediction.PredictionResponse;
import com.asa.web.model.Prediction;
import com.asa.web.repository.PredictionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final PredictionRepository predictionRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OllamaChatModel ollamaChatModel;
    private final VectorStore vectorStore;
    private final CurrentUserService currentUserService;

    public Prediction createPrediction(String question) {
        UUID currentUserId = currentUserService.getCurrentUserId();
        String ragPrompt = """
            You are a sports prediction assistant. 
            Use retrieved sports data to answer the question. 
            Respond ONLY in valid JSON format:

            {
              "answer": "short direct answer",
              "explanation": "detailed reasoning",
              "confidence": 0.0-1.0
            }

            Question: %s
            """.formatted(question);

        var qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
            .searchRequest(
                SearchRequest
                    .builder()
                    .filterExpression("userId == '" + currentUserId + "'")
                    .similarityThreshold(0.8d)
                    .topK(6).build()
                )
            .build();

        String response = ChatClient.builder(ollamaChatModel)
            .build()
            .prompt()
            .advisors(qaAdvisor)
            .user(ragPrompt)
            .call()
            .content();

        try {
            // Parse JSON into DTO
            PredictionResponse parsed = objectMapper.readValue(response, PredictionResponse.class);
            
            Prediction prediction = Prediction.builder()
                    .question(question)
                    .answer(parsed.getAnswer())
                    .explanation(parsed.getExplanation())
                    .confidenceScore(parsed.getConfidence())
                    .build();

            return predictionRepository.save(prediction);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse LLM response: " + response, e);
        }
    }

    public List<Prediction> getAllPredictions() {
        return predictionRepository.findAll();
    }

    public Prediction getPredictionById(Long id) {
        return predictionRepository.findById(id).orElse(null);
    }
}
