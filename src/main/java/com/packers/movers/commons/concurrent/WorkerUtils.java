package com.packers.movers.commons.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WorkerUtils {
    private static final Logger LOG = LoggerFactory.getLogger(WorkerUtils.class);

    public static void startWorkers(List<Worker> workers) {
        for (Worker worker : workers) {
            startWorker(worker);
        }
    }

    public static void stopWorkers(List<Worker> workers) {
        for (Worker worker : workers) {
            stopWorker(worker);
        }
    }

    private static void startWorker(Worker worker) {
        if (worker == null || worker.isRunning()) {
            return;
        }

        String className = worker.getClass().getName();
        LOG.trace("Starting {}", className);
        worker.start();
        LOG.trace("{} was started", className);
    }

    private static void stopWorker(Worker worker) {
        if (worker == null || !worker.isRunning()) {
            return;
        }

        String className = worker.getClass().getName();
        LOG.trace("Stopping {}", className);
        worker.stop();
        LOG.trace("{} was stopped", className);
    }
}
