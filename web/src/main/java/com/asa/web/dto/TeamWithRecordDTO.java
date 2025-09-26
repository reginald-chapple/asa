package com.asa.web.dto;

import com.asa.web.model.League;
import com.asa.web.model.Sport;

public record TeamWithRecordDTO(
    Long teamId,
    Long teamSeasonRecordId,
    String name,
    League league,
    Sport sport,
    Integer season,
    Integer wins,
    Integer losses,
    Integer ties
) {}

