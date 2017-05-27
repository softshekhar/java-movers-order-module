package com.packers.movers.commons.config.contracts;

import com.packers.movers.commons.config.utils.Property;

public interface ApplicationConfiguration {
    @Property(key = "host.port")
    int getPort();

    @Property(key = "host.public.name")
    String getPublicHost();


    @Property(key = "host.context.path")
    String getContextPath();
}
