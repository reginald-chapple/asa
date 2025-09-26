package com.asa.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentIngestionService {

    @Value("classpath:/documents/ACSST1Y2024.S1703-2025-09-16T043613.csv")
    private Resource document;

    // private final VectorStore vectorStore;

    // @Override
    // public void run(String... args) throws Exception {
    //     // Read the file
    //     TikaDocumentReader documentReader = new TikaDocumentReader(document);

    //     // Split file into chunks
    //     TextSplitter textSplitter = new TokenTextSplitter();
    //     List<Document> documents = textSplitter.split(documentReader.read());

    //     // Store the data in the vector database
    //     vectorStore.accept(documents);
    // }

}
