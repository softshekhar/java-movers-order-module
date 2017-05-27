package com.packers.movers.test.utils;

import com.packers.movers.server.db.FlywayMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class TestDatabaseUtils {
    private static final Logger LOG = LoggerFactory.getLogger(TestDatabaseUtils.class);

    public static void migrateDatabase(SingleConnectionDataSource dataSource) {
        try {
            LOG.trace("Migrating database");
            dataSource.initConnection();
            FlywayMigration flywayMigration = new FlywayMigration(dataSource);
            flywayMigration.migrate("main");
        } catch (Exception e) {
            LOG.trace(e.getMessage(), e);
        }
    }
}