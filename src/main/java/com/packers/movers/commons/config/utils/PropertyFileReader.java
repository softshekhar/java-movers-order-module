package com.packers.movers.commons.config.utils;

import org.constretto.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyFileReader {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyFileReader.class);

    public static Map<String, String> readConfiguration(Resource resource) {
        if (!resource.exists()) {
            LOG.warn("Resource \"{}\" does not exist", resource);
            return new HashMap<>();
        }

        Properties properties = new Properties();

        try (InputStream inputStream = resource.getInputStream()) {
            properties.load(inputStream);

        } catch (IOException exception) {
            LOG.error(String.format("Failed to load properties from resource \"%s\"", resource), exception);
            return new HashMap<>();
        }

        Map<String, String> propertiesMap = new HashMap<>();
        for (String propertyKey : properties.stringPropertyNames()) {
            propertiesMap.put(propertyKey, properties.getProperty(propertyKey));
        }

        return propertiesMap;
    }

    public static Map<String, String> readConfiguration(Resource... resources) {
        Map<String, String> combinedProperties = new HashMap<>();

        for (Resource resource : resources) {
            Map<String, String> properties = readConfiguration(resource);
            combinedProperties.putAll(properties);
        }

        return combinedProperties;
    }

    public static Map<String, String> readConfiguration(Iterable<Resource> resources) {
        Map<String, String> combinedProperties = new HashMap<>();

        for (Resource resource : resources) {
            Map<String, String> properties = readConfiguration(resource);
            combinedProperties.putAll(properties);
        }

        return combinedProperties;
    }
}
