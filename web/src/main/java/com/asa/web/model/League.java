package com.asa.web.model;


public enum League {
    MLB("Major League Baseball"),
    NBA("National Basketball Association"),
    NHL("National Hockey League"),
    NFL("National Football League"),
    MLS("Major League Soccer");

    private final String value;

    League(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
