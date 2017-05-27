package com.packers.movers.datalayer.entities;

import java.util.HashMap;
import java.util.Map;

public class SqlParameterBuilder {
    private Map<String, Object> parameters = new HashMap();

    private SqlParameterBuilder() {
    }

    public static SqlParameterBuilder builder() {
        return new SqlParameterBuilder();
    }

    public static Map<String, Object> singleParameter(String key, Object value) {
        return builder().addParameter(key, value).build();
    }

    public SqlParameterBuilder addParameter(String key, Object value) {
        this.parameters.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return this.parameters;
    }
}
