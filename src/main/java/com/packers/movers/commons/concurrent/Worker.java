package com.packers.movers.commons.concurrent;

public interface Worker {
    void start();
    void stop();
    boolean isRunning();
}
