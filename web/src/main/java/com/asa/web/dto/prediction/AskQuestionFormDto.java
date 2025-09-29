package com.asa.web.dto.prediction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AskQuestionFormDto {

    @NotBlank
    @Length(max = 500)
    private String question;

    List<UUID> filters = new ArrayList<>();

}
