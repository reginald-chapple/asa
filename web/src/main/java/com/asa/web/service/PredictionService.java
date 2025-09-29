package com.asa.web.service;

import java.util.List;
import java.util.Set;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.asa.web.component.FilterExpressionBuilder;
import com.asa.web.dto.prediction.PredictionResponse;
import com.asa.web.model.Filter;
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
    private final FilterExpressionBuilder filterExpressionBuilder;

    private String cleanResponse(String response) {
        if (response == null) return "";
        // Remove ```json ... ```
        return response
            .replaceAll("```json", "")
            .replaceAll("```", "")
            .trim();
    }

    public Prediction createPrediction(String question, Set<Filter> appliedFilters) {
        String ragPrompt = """
            You are a data analyst assistant.
            Use retrieved sports data to answer the question. 

            Respond ONLY in raw JSON. 
            Do NOT include markdown code fences (no ```json). 

            Respond ONLY in valid JSON format with the following fields:
            {
                "answer": "short direct answer",
                "explanation": "detailed reasoning",
                "confidence": 0.0-1.0
            }

            If you do not know the answer or the context is insufficient, 
            still respond with valid JSON, but set:
            "answer": "unknown",
            "explanation": "Not enough information retrieved.",
            "confidence": 0.0

            Question: %s
        """.formatted(question);

        // Build filter expression dynamically
        String filterExpression = filterExpressionBuilder.buildExpression(appliedFilters);

        // System.out.println("Filter expression: " + filterExpression);

    //     SearchRequest testRequest = SearchRequest
    // .builder()
    // .query("Lions vs Packers") // Simplified query
    // .filterExpression("league == 'NFL'")
    // .topK(10)
    // .similarityThreshold(0.0) // Explicitly set to ensure no filtering
    // .build();
// List<Document> testDocs = vectorStore.similaritySearch(testRequest);
// System.out.println("Retrieved " + testDocs.size() + " docs with filter 'league == ''NFL''':");
// testDocs.forEach(doc -> System.out.println("Content: " + doc.getText() + ", Metadata: " + doc.getMetadata()));

        var qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
            .searchRequest(SearchRequest
                .builder()
                .topK(6)
                .query(question)
                .filterExpression(filterExpression)
                .similarityThreshold(0.0)
                .build())
            .build();
        String response = ChatClient.builder(ollamaChatModel)
            .build()
            .prompt()
            .advisors(qaAdvisor)
            .user(ragPrompt)
            .call()
            .content();

        try {
            String cleaned = cleanResponse(response);
            PredictionResponse parsed = objectMapper.readValue(cleaned, PredictionResponse.class);

            Prediction prediction = Prediction.builder()
                .question(question)
                .answer(parsed.getAnswer())
                .explanation(parsed.getExplanation())
                .confidenceScore(parsed.getConfidence())
                .filters(appliedFilters)
                .build();

            return predictionRepository.save(prediction);

        } catch (Exception e) {
            // Fallback JSON if parsing fails
            Prediction prediction = Prediction.builder()
                .question(question)
                .answer("unknown")
                .explanation("Model did not return valid JSON. Raw response: " + response)
                .confidenceScore(0.0)
                .filters(appliedFilters)
                .build();

            return predictionRepository.save(prediction);
        }

    }

    public List<Prediction> getAllPredictions() {
        return predictionRepository.findAll(Sort.by("createdAt").descending());
    }

    public Prediction getPredictionById(Long id) {
        return predictionRepository.findById(id).orElse(null);
    }
}
