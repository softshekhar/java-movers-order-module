package com.packers.movers.configuration;


import com.packers.movers.commons.config.contracts.ApplicationConfiguration;
import com.packers.movers.commons.config.contracts.ServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.util.Map;

import static com.packers.movers.commons.config.utils.ConfigurationUtils.mapProperties;
import static com.packers.movers.commons.config.utils.ConfigurationUtils.readProperties;


public class Configuration {
    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    private static final DatabaseConfiguration DATABASE_CONFIGURATION;

    private static final ApplicationConfiguration APPLICATION_CONFIGURATION;

    private static final ServerConfiguration SERVER_CONFIGURATION;

    private Configuration() {
    }

    static {
        try {
            LOG.trace("Reading configuration properties");

            Map<String, String> properties = readProperties("classpath:application.properties", "classpath:com.packers.movers.test-application.properties");

            DATABASE_CONFIGURATION = mapProperties(DatabaseConfiguration.class, properties);
            APPLICATION_CONFIGURATION = mapProperties(ApplicationConfiguration.class, properties);
            SERVER_CONFIGURATION = new ServerConfiguration(APPLICATION_CONFIGURATION);

        } catch (Exception exception) {
            LOG.error("Failed to read configuration properties", exception);
            throw new RuntimeException(exception);
        }
    }

    public static DriverManagerDataSource createDriverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
            DATABASE_CONFIGURATION.getUrl(),
            DATABASE_CONFIGURATION.getUsername(),
            DATABASE_CONFIGURATION.getPassword()
        );
        dataSource.setDriverClassName(DATABASE_CONFIGURATION.getDriver());
        return dataSource;
    }

    public static SingleConnectionDataSource createSingleConnectionDataSource() {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource(
            DATABASE_CONFIGURATION.getUrl(),
            DATABASE_CONFIGURATION.getUsername(),
            DATABASE_CONFIGURATION.getPassword(),
            DATABASE_CONFIGURATION.getSuppressClose()
        );
        dataSource.setDriverClassName(DATABASE_CONFIGURATION.getDriver());
        return dataSource;
    }

    public static DatabaseConfiguration getDatabaseConfiguration() {
        return DATABASE_CONFIGURATION;
    }

    public static ApplicationConfiguration getApplicationConfiguration() {
        return APPLICATION_CONFIGURATION;
    }

    public static ServerConfiguration getServerConfiguration() {
        return SERVER_CONFIGURATION;
    }
}