package com.asa.web.service;

import java.io.IOException;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileValidationService {

    private final Tika tika = new Tika();
    
    // Define allowed MIME types as constants
    public static final String PDF_MIME_TYPE = "application/pdf";
    public static final String CSV_MIME_TYPE = "text/csv";

    public boolean isValidFileType(MultipartFile file) throws IOException {
        return isValidFileType(file, PDF_MIME_TYPE, CSV_MIME_TYPE);
    }

    public boolean isValidFileType(MultipartFile file, String... allowedMimeTypes) throws IOException {
        if (file == null || file.isEmpty()) {
            log.warn("File validation failed: file is null or empty");
            return false;
        }

        try {
            String detectedMimeType = tika.detect(file.getInputStream(), file.getOriginalFilename());
            log.info("Detected MIME type: {} for file: {}", detectedMimeType, file.getOriginalFilename());

            for (String allowedType : allowedMimeTypes) {
                if (detectedMimeType.equals(allowedType)) {
                    return true;
                }
            }
            
            log.warn("File type not allowed. Detected: {}, Allowed: {}", detectedMimeType, String.join(", ", allowedMimeTypes));
            return false;
            
        } catch (IOException e) {
            log.error("Error detecting file type for file: {}", file.getOriginalFilename(), e);
            throw e;
        }
    }

    public String getDetectedMimeType(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        return tika.detect(file.getInputStream(), file.getOriginalFilename());
    }
}

