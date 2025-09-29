package com.asa.web.dto.rag;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.drew.lang.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadDto {

    @NotNull
    private MultipartFile file;

    private List<UUID> filters = new ArrayList<>();
}
