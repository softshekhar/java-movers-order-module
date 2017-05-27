package com.packers.movers.configuration;


import com.packers.movers.commons.config.utils.Property;

public interface DatabaseConfiguration {

    @Property(key = "environment.jdbc.driver")
    String getDriver();

    @Property(key = "environment.jdbc.url")
    String getUrl();

    @Property(key = "environment.jdbc.user")
    String getUsername();

    @Property(key = "environment.jdbc.password")
    String getPassword();

    @Property(key = "environment.jdbc.suppress.close")
    boolean getSuppressClose();
}