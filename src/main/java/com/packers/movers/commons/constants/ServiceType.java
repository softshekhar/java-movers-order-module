package com.packers.movers.commons.constants;

import com.packers.movers.commons.utils.Sequence;

public enum ServiceType {
    Packing,
    Moving,
    Cleaning;

    public static ServiceType parse(String name) {
        return  Sequence.of(ServiceType.values()).first(type -> type.name().equalsIgnoreCase(name));
    }
}
