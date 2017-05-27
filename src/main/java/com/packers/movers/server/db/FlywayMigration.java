package com.packers.movers.server.db;

import com.packers.movers.configuration.Configuration;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class FlywayMigration {
    private static final Logger LOG = LoggerFactory.getLogger(FlywayMigration.class);
    private static final String SCHEMA_NAME = "message-handler";

    private DriverManagerDataSource dataSource;

    public FlywayMigration(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void migrate(String schemaName) {
        LOG.info("Migrating database: {}", dataSource.getUrl());
        Flyway flyway = setupFlyway(schemaName);
        flyway.repair();
        flyway.migrate();
    }

    public void clean(String schemaName) {
        LOG.info("Cleaning database: {}", dataSource.getUrl());
        Flyway flyway = setupFlyway(schemaName);
        flyway.clean();
        flyway.migrate();
    }

    private Flyway setupFlyway(String schemaName) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setSchemas(schemaName);
        flyway.setBaselineOnMigrate(true);

        return flyway;
    }

    public static void main(String[] args) {
        DriverManagerDataSource dataSource = Configuration.createDriverManagerDataSource();
        FlywayMigration migration = new FlywayMigration(dataSource);

        if (args != null && args.length > 0 && args[0].equalsIgnoreCase("clean")) {
            migration.clean(SCHEMA_NAME);
            return;
        }

        migration.migrate(SCHEMA_NAME);
    }
}
