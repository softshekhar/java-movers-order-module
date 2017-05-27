package com.packers.movers.server;

import com.packers.movers.commons.config.contracts.ServerConfiguration;
import com.packers.movers.commons.utils.LogUtils;
import com.packers.movers.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private final ApplicationServer server;

    public Main() throws Exception {
        ServerConfiguration serverConfiguration = Configuration.getServerConfiguration();
        this.server = new ApplicationServer(serverConfiguration);
    }

    public static void main(String[] args) throws Exception {
        try {
            LOG.trace("Application booting");

            LogUtils.initializeLogger(Level.INFO);

            final Main main = new Main();

            main.server.start();
            main.server.waitForCompletion();

        } catch (RuntimeException exception) {
            LOG.error("Runtime exception", exception);
            System.exit(1);
        }
    }
}