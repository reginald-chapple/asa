package com.asa.web.dto.prediction;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String question;

    @NotBlank
    private String answer;

    private String explanation;

    @NotBlank
    private Double confidenceScore;

}
