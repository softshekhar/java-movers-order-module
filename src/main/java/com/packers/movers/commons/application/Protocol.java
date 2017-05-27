package com.packers.movers.commons.application;

public enum Protocol {
    HTTP, HTTPS;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}