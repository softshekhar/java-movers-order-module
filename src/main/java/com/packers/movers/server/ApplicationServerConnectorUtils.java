package com.packers.movers.server;

import com.packers.movers.commons.application.Protocol;
import com.packers.movers.commons.config.contracts.ServerConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationServerConnectorUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationServerConnectorUtils.class);

    public static ServerConnector createServerConnector(ServerConfiguration serverConfiguration, Server jettyServer) {
        ServerConnector connector = new ServerConnector(jettyServer);
        connector.setPort(serverConfiguration.getPort());
        serverConfiguration.setProtocol(Protocol.HTTP);

        return connector;
    }
}
