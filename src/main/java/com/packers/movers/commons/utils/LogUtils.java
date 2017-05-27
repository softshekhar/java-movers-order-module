package com.packers.movers.commons.utils;

import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.LogManager;

public class LogUtils {

    public static void initializeLogger(Level logLevel) {
        LogManager.getLogManager().reset();

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        LogManager.getLogManager().getLogger("").setLevel(logLevel);
    }

}
