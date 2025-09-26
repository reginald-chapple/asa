package com.asa.web.model;

public enum Sport {

    BASEBALL("Baseball"),
    BASKETBALL("Basketball"),
    FOOTBALL("Football"),
    HOCKEY("Hockey"),
    SOCCER("Soccer");

    private final String value;

    Sport(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
