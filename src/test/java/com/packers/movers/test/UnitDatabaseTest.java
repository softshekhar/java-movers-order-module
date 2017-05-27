package com.packers.movers.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.testng.annotations.BeforeMethod;
import com.packers.movers.test.utils.TestDatabaseUtils;

import java.sql.SQLException;

public class UnitDatabaseTest extends UnitTest {
    private static final Logger LOG = LoggerFactory.getLogger(UnitDatabaseTest.class);

    private SingleConnectionDataSource dataSource;

    @BeforeMethod
    public void beforeMethod() throws SQLException {
        dataSource = (SingleConnectionDataSource) applicationContext.getBean("dataSource");
        TestDatabaseUtils.migrateDatabase(dataSource);
    }
}