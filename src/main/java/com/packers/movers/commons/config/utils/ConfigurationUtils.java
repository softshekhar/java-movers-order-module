package com.packers.movers.commons.config.utils;

import com.packers.movers.commons.utils.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.constretto.model.Resource;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationUtils.class);

    public static Map<String, String> readEnvironmentVariables() {
        return System.getenv();
    }

    public static Map<String, String> readPropertiesFile(String path) {
        return PropertyFileReader.readConfiguration(Resource.create(path));
    }

    public static Map<String, String> readPropertiesFiles(String... paths) {
        return PropertyFileReader.readConfiguration(Sequence.of(paths).map(Resource::create));
    }


    @SafeVarargs
    public static Map<String, String> combineProperties(Map<String, String>... properties) {
        HashMap<String, String> combinedProperties = new HashMap<>();

        for (Map<String, String> props : properties) {
            combinedProperties.putAll(props);
        }

        return combinedProperties;
    }


    public static <T> T mapProperties(Class<T> configurationClass, Map<String, String> properties) {
        ConfigurationMapper mapper = new ConfigurationMapper(properties);
        return mapper.mapProperties(configurationClass);
    }

    public static <T> T mapProperties(Class<T> configurationClass, Map<String, String>... properties) {
        Map<String, String> combinedProperties = combineProperties(properties);
        return mapProperties(configurationClass, combinedProperties);
    }

    public static Map<String, String> readProperties(String... paths) {

        return readPropertiesFiles(paths);
    }
}
