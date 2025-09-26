package com.asa.web.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class GameFormDto {
    private Long homeTeamId;
    private Long awayTeamId;
    private LocalDate gameDate;
    private String week;
}
