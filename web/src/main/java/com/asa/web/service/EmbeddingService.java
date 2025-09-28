package com.asa.web.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmbeddingService {

    private final VectorStore vectorStore;
    private final CurrentUserService  currentUserService;

    public void generate(Resource document) {

        UUID currentUserId = currentUserService.getCurrentUserId();

        try {
            log.info("Starting embedding generation for document (userId={})", currentUserId);
            
            // Read the file using Tika
            TikaDocumentReader documentReader = new TikaDocumentReader(document);
            List<Document> readDocuments = documentReader.read();
            
            log.info("Read {} document(s) from file", readDocuments.size());

            // Split file into chunks
            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> splitDocuments = textSplitter.split(readDocuments);
            
            log.info("Split into {} chunks", splitDocuments.size());

            for (Document doc : splitDocuments) {
                doc.getMetadata().put("userId", currentUserId.toString());
            }

            // Store the data in the vector database
            vectorStore.accept(splitDocuments);
            
            log.info("Successfully stored embeddings in vector database");
            
        } catch (Exception e) {
            log.error("Error generating embeddings: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate embeddings", e);
        }
    }

    public Resource createResource(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }
        
        // Create resource from MultipartFile input stream
        // Note: We're setting a filename to help with content detection
        return new InputStreamResource(file.getInputStream()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
    }
}
