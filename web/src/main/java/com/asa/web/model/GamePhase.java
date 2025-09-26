package com.asa.web.model;

public enum GamePhase {
    SCHEDULED("Scheduled"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    POSTPONED("Postponed");

    private final String value;

    GamePhase(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
