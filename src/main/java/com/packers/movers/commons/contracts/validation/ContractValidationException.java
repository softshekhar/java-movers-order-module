package com.packers.movers.commons.contracts.validation;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class ContractValidationException extends Exception {
    private final List<String> validationErrors;

    public ContractValidationException(List<String> validationErrors) {
        super(createErrorMessage(validationErrors));
        this.validationErrors = validationErrors;
    }

    public ContractValidationException(String validationError) {
        super(validationError);
        this.validationErrors = Arrays.asList(validationError);
    }

    private static String createErrorMessage(List<String> validationErrors) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (String validationError : validationErrors) {
            stringJoiner.add(validationError);
        }
        return stringJoiner.toString();
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}
