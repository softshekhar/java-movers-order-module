package com.packers.movers.commons.contracts;

import java.time.Instant;

public class TimestampContract extends ContractBase {
    private String timestamp;

    public TimestampContract() {
        this.timestamp = Instant.now().toString();
    }

    public TimestampContract(Instant timestamp) {
        this.timestamp = timestamp.toString();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Instant toInstant() {
        return Instant.parse(timestamp);
    }
}
