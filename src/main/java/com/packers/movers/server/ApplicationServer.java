package com.packers.movers.server;

import com.packers.movers.commons.concurrent.Worker;
import com.packers.movers.commons.concurrent.WorkerUtils;
import com.packers.movers.commons.config.contracts.ServerConfiguration;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import javax.ws.rs.core.Application;
import java.util.ArrayList;
import java.util.List;

public class ApplicationServer extends Application {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationServer.class);

    private static final String APPLICATION_PACKAGE = "com.packers.movers";

    private final ApplicationContextLoaderListener contextLoaderListener = new ApplicationContextLoaderListener();
    private final Server jettyServer;
    private final List<Worker> workers;
    private ApplicationContext applicationContext;

    private boolean isStarted;
    private ServerConfiguration configuration;
    private ResourceConfig jerseyResourceConfig;

    public ApplicationServer(ServerConfiguration configuration) {
        this.configuration = configuration;

        this.jettyServer = new Server();
        this.workers = new ArrayList<>();
        this.isStarted = false;
    }

    public void start() throws Exception {
        LOG.trace("Starting application server");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.trace("Shutdown application");
            stop();
        }));

        ServletContextHandler context = loadContext(configuration);
        ServerConnector connector = ApplicationServerConnectorUtils.createServerConnector(configuration, jettyServer);

        jettyServer.addConnector(connector);
        jettyServer.setHandler(context);

        startApplication(configuration);
        WorkerUtils.startWorkers(workers);
    }

    public void waitForCompletion() {
        joinApplicationThread();
    }

    public void stop() {
        try {
            LOG.trace("Stopping application server");
            isStarted = false;

            WorkerUtils.stopWorkers(workers);
            jettyServer.stop();
        } catch (Exception exception) {
            LOG.warn("Error when stopping jetty server", exception);
        }
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public boolean isStarted() {
        return this.isStarted && this.jettyServer.isStarted();
    }

    public ServerConfiguration getConfiguration() {
        return this.configuration;
    }

    protected String getContextConfigurationLocation() {
        return "classpath:context.xml";
    }

    protected void registerPath(ConstraintSecurityHandler handler, Constraint constraint, String path) {
        LOG.trace("Register path: {}", path);

        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setConstraint(constraint);
        mapping.setPathSpec(path);

        handler.addConstraintMapping(mapping);
    }

    private ServletContextHandler loadContext(ServerConfiguration configuration) {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath(configuration.getContextPath());


        jerseyResourceConfig = new ResourceConfig();
        jerseyResourceConfig.register(MultiPartFeature.class);
        jerseyResourceConfig.packages(APPLICATION_PACKAGE);

        ServletContainer servletContainer = new ServletContainer(jerseyResourceConfig);
        ServletHolder jerseyServlet = new ServletHolder(servletContainer);

        context.addServlet(jerseyServlet, "/*");
        context.addEventListener(contextLoaderListener);

        final String contextLocationParameter = "contextConfigLocation";
        String contextConfigLocation = getContextConfigurationLocation();
        context.setInitParameter(contextLocationParameter, contextConfigLocation);
        LOG.trace("{}: {}", contextLocationParameter, contextConfigLocation);

        return context;
    }


    private void startApplication(ServerConfiguration serverConfiguration) throws Exception {
        jettyServer.start();
        isStarted = true;

        String publicUrl = serverConfiguration.getPublicUrl().toString();
        LOG.trace("Application Server is started on URL: {}", publicUrl);
        LOG.trace("Application Server health check on URL: {}{}", publicUrl, "/health");
    }

    private void joinApplicationThread() {
        try {
            jettyServer.join();
        } catch (InterruptedException exception) {
            LOG.trace("Jetty server thread completed");
        }
    }

    private class ApplicationContextLoaderListener extends ContextLoaderListener {

        @Override
        public void contextInitialized(ServletContextEvent event) {
            applicationContext = this.initWebApplicationContext(event.getServletContext());
            LOG.trace("Application context is initialized");
        }
    }

}
