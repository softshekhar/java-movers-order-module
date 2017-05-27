package com.packers.movers.commons.constants;

import com.packers.movers.commons.utils.Sequence;

public enum StatusType {
    Created,
    Inprogress,
    Done;

    public static StatusType parse(String value) {
        return  Sequence.of(StatusType.values()).first(type -> type.name().equalsIgnoreCase(value));
    }
}
