package com.packers.movers.datalayer.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class SQLClient {
    private static final Logger LOG = LoggerFactory.getLogger(SQLClient.class);
    private SingleConnectionDataSource singleConnectionDataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate singleConnectionTemplate;

    public SQLClient(DataSource dataSource, SingleConnectionDataSource singleConnectionDataSource) {
        this.singleConnectionDataSource = singleConnectionDataSource;
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.singleConnectionTemplate = new NamedParameterJdbcTemplate(singleConnectionDataSource);
    }

    public SingleConnectionDataSource getSingleConnectionDataSource() {
        return singleConnectionDataSource;
    }

    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public NamedParameterJdbcTemplate getSingleConnectionTemplate() {
        return singleConnectionTemplate;
    }

    public int update(String sql, Map<String, Object> parameters) {
        return Integer.valueOf(this.jdbcTemplate.update(sql, parameters));
    }

}
