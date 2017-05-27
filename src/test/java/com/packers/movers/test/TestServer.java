package com.packers.movers.test;

import com.jayway.restassured.RestAssured;
import com.packers.movers.server.ApplicationServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class TestServer {
    private static final Logger LOG = LoggerFactory.getLogger(TestServer.class);

    private ApplicationServer server;
    private ApplicationContext applicationContext;
    private String url;
    private Exception serverException;

    public void start() throws Exception {
        startServer();

        applicationContext = server.getApplicationContext();

        RestAssured.port = server.getConfiguration().getPort();
        RestAssured.basePath = server.getConfiguration().getContextPath();

        url = server.getConfiguration().getPublicUrl().toString();
    }

    public void stop() {
        server.stop();
    }

    public String getUrl() {
        return url;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    private void startServer() throws Exception {
        server = new TestApplicationServer();
        server.start();
    }
}
