package com.packers.movers.commons.utils.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Parameter;

public class ValidationUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ValidationUtils.class);

    public static  <T> T createValidationProxy(final T instance) throws Exception {
        MethodInterceptor methodInterceptor = (proxy, method, arguments, methodProxy) -> {
            LOG.trace("Invoke method: {} through proxy", method.getName());

            Parameter[] parameters = method.getParameters();
            for (int index = 0; index < parameters.length; index++) {
                Parameter parameter = parameters[index];
                Object argument = arguments[index];

                validateNotNull(parameter, argument);
                validatePositive(parameter, argument);
            }

            return method.invoke(instance, arguments);
        };

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(instance.getClass());
        enhancer.setCallback(methodInterceptor);

        return (T) enhancer.create();
    }

    private static void validateNotNull(Parameter parameter, Object argument) throws Exception {
        NotNull notNullAnnotation = parameter.getAnnotation(NotNull.class);

        boolean hasAnnotation = notNullAnnotation != null;
        if (!hasAnnotation) {
            return;
        }

        boolean argumentHasValue = argument != null;
        if (argumentHasValue) {
            return;
        }

        String errorMessage = String.format("Argument %s (%s) cannot be null", parameter.getName(), parameter.getType().getSimpleName());
        throw new ValidationException(errorMessage);
    }

    private static void validatePositive(Parameter parameter, Object argument) throws Exception {
        Positive positiveAnnotation = parameter.getAnnotation(Positive.class);

        boolean hasAnnotation = positiveAnnotation != null;
        if (!hasAnnotation) {
            return;
        }

        boolean isPositive = isPositiveNumber(argument);
        if (isPositive) {
            return;
        }

        String errorMessage = String.format("Argument %s (%s) should be a positive number", parameter.getName(), parameter.getType().getSimpleName());
        throw new ValidationException(errorMessage);
    }

    private static boolean isPositiveNumber(Object value) {
        if (value instanceof Integer) {
            return (Integer) value >= 0;
        }

        if (value instanceof Float) {
            return (Float) value >= 0;
        }

        if (value instanceof Double) {
            return (Double) value >= 0;
        }

        if (value instanceof Long) {
            return (Long) value >= 0;
        }

        return false;
    }
}
