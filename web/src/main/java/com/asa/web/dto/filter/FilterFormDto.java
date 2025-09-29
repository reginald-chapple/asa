package com.asa.web.dto.filter;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterFormDto {
    @NotBlank
    private String name;

    @NotBlank
    private String value;
}
