package com.packers.movers.commons.contracts;

import java.util.Arrays;
import java.util.Collection;

public class ErrorContract extends ContractBase {
    private String[] errors;

    public ErrorContract(String[] errors) {
        this.errors = Arrays.copyOf(errors, errors.length);
    }

    public ErrorContract(Collection<String> errors) {
        this(errors.toArray(new String[errors.size()]));
    }

    public ErrorContract(String error) {
        this.errors = new String[] { error };
    }

    private ErrorContract() {
        this((String[]) null);
    }

    public String[] getErrors() {
        return Arrays.copyOf(errors, errors.length);
    }

    public String getError(int errorIndex) {
        return errors[errorIndex];
    }
}
