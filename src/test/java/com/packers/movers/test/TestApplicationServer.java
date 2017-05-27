package com.packers.movers.test;


import com.packers.movers.configuration.Configuration;
import com.packers.movers.server.ApplicationServer;

public class TestApplicationServer extends ApplicationServer {

    public TestApplicationServer() throws Exception {
        super(Configuration.getServerConfiguration());
    }

    @Override
    protected String getContextConfigurationLocation() {
        return "classpath:test-context.xml";
    }
}
