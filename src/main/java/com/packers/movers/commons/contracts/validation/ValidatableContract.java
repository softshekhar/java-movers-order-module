package com.packers.movers.commons.contracts.validation;

public interface ValidatableContract {
    void validate() throws ContractValidationException;
}
