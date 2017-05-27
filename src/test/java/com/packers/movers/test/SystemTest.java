package com.packers.movers.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class SystemTest {
    private static final Logger LOG = LoggerFactory.getLogger(SystemTest.class);

    protected static TestServer testServer;
    protected static ApplicationContext context;

    @BeforeSuite
    public void startServer() throws Exception {
        LOG.trace("Start com.packers.movers.test server");

        testServer = new TestServer();
        testServer.start();

        context = testServer.getApplicationContext();
    }

    @AfterSuite
    protected void stop() throws Exception {
        LOG.trace("Stopping com.packers.movers.test server");
        testServer.stop();
    }
}
