package com.packers.movers.commons.config.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class ConfigurationMapper {
    private final static Logger LOG = LoggerFactory.getLogger(ConfigurationMapper.class);

    private final Map<String, String> configurationSource;

    public ConfigurationMapper(Map<String, String> configurationSource) {
        this.configurationSource = configurationSource;
    }

    public <T> T mapProperties(Class<T> type) {
        T result = createInterface(type);
        validate(type, result);

        return result;
    }

    private <T> T createInterface(Class<T> interfaceType) {
        Class[] interfaces = new Class[] { interfaceType };

        InvocationHandler invocationHandler = new InvocationHandler() {
            private Map<String, Object> mappedConfiguration = new HashMap<>();

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Property property = method.getAnnotation(Property.class);
                if (property == null) {
                    return null;
                }

                String key = property.key();
                boolean required = property.required();

                boolean hasMappedConfiguration = mappedConfiguration.containsKey(key);
                if (hasMappedConfiguration) {
                    return mappedConfiguration.get(key);
                }

                boolean keyExists = configurationSource.containsKey(key);
                if (!keyExists && required) {
                    String message = String.format("Configuration key does not exist - %s", key);
                    throw new Exception(message);
                }

                String value = configurationSource.get(key);
                Object parsedValue = parseValue(value, method.getReturnType());

                if (parsedValue == null && required) {
                    String message = String.format("Failed to parse value - %s", value);
                    throw new Exception(message);
                }

                LOG.trace("Has mapped configuration for key : {} with value: {}", key, value);
                mappedConfiguration.put(key, parsedValue);

                return parsedValue;
            }
        };

        return  (T) java.lang.reflect.Proxy.newProxyInstance(
            interfaceType.getClassLoader(),
            interfaces,
            invocationHandler);
    }

    private Object parseValue(String value, Class type) {
        try {
            if (type.equals(URI.class)) {
                return new URI(value);
            }

            if (type.equals(URL.class)) {
                return new URL(value);
            }

            if (type.equals(UUID.class)) {
                return UUID.fromString(value);
            }

            if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                return value.toLowerCase().equals("true");
            }

            if (type.equals(int.class) || type.equals(Integer.class)) {
                return Integer.parseInt(value);
            }

            if (type.equals(double.class) || type.equals(Double.class)) {
                return Double.parseDouble(value);
            }

            return type.cast(value);

        } catch (Exception exception) {
            String message = String.format("Failed to parse value \"%s\" to %s", value, type.getSimpleName());
            throw new RuntimeException(message);
        }
    }

    private <T> void validate(Class type, T proxy) {
        List<String> errors = new ArrayList<>();
        Method[] methods = type.getMethods();

        for (Method method : methods) {
            Property property = method.getAnnotation(Property.class);
            if (property == null) {
                continue;
            }

            try {
                method.setAccessible(true);
                method.invoke(proxy);
            } catch (Exception exception) {
                String reason = getReason(exception);
                String message = String.format("%s.%s - Reason : %s", type.getSimpleName(), method.getName(), reason);

                errors.add(message);
            }
        }

        boolean hasErrors = !errors.isEmpty();
        if (hasErrors) {
            String errorMessage = getErrorMessage("Missing configuration for property : ", errors);
            throw new RuntimeException(errorMessage);
        }
    }

    private String getReason(Exception exception) {
        Throwable cause = exception;

        do {
            String reason = cause.getMessage();
            if (reason != null) {
                return reason;
            }

            cause = cause.getCause();
        } while (cause != null);

        return "Unknown";
    }

    private String getErrorMessage(String message, List<String> errors) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append(message);
        stringBuilder.append("\n");

        for (String error : errors) {
            stringBuilder.append(error);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
