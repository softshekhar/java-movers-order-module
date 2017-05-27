package com.packers.movers.commons.contracts;

import com.packers.movers.commons.contracts.validation.ContractValidationException;
import com.packers.movers.commons.contracts.validation.NotEmpty;
import com.packers.movers.commons.contracts.validation.ValidatableContract;
import com.packers.movers.commons.utils.JsonUtils;
import com.packers.movers.commons.utils.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContractBase implements ValidatableContract {
    private static final Logger LOG = LoggerFactory.getLogger(ContractBase.class);

    public String toJson() {
        return JsonUtils.serialize(this);
    }

    @Override
    public String toString() {
        return toJson();
    }

    @Override
    public void validate() throws ContractValidationException {
        Class type = getClass();
        LOG.trace("Validating class {}", type.getSimpleName());

        List<String> validationErrors = new ArrayList<>();
        Sequence<Field> fields = getFields(type, Sequence.of())
            .filter(field -> !Modifier.isStatic(field.getModifiers()));

        for (Field field : fields) {
            Object value = getValue(field);
            validationErrors.addAll(getValidationErrors(field, value));
        }
        if (!validationErrors.isEmpty()) {
            throw new ContractValidationException(validationErrors);
        }
    }

    private Sequence<Field> getFields(Class currentClass, Sequence<Field> fields) {
        Class superClass = currentClass.getSuperclass();
        if (superClass != null) {
            fields = getFields(superClass, fields);
        }

        return fields.merge(currentClass.getDeclaredFields());
    }

    private Object getValue(Field field) {
        try {
            field.setAccessible(true);
            return field.get(this);

        } catch (IllegalAccessException exception) {
            throw new RuntimeException("Failed to retrieve field value", exception);
        }
    }

    private List<String> getValidationErrors(Field field, Object value) {
        List<String> validationErrors = new ArrayList<>();

        boolean isOptional = field.isAnnotationPresent(com.packers.movers.commons.contracts.validation.Optional.class);
        boolean isNull = value == null;
        boolean isEmptyString = value instanceof String && ((String) value).isEmpty();
        boolean isValidatableContract = value instanceof ValidatableContract;
        boolean isCollection = value instanceof Collection;
        boolean isArray = value instanceof Object[];

        if (!isOptional && isNull) {
            LOG.trace("Field {}: Invalid - Null", field.getName());
            validationErrors.add(String.format("Parameter '%s' can not be null", field.getName()));

        } else if (!isOptional && isEmptyString) {
            LOG.trace("Field {}: Invalid - Empty string", field.getName());
            validationErrors.add(String.format("Parameter '%s' can not be empty", field.getName()));

        } else if (isValidatableContract) {
            LOG.trace("Field {}:", field.getName());
            validationErrors.addAll(getValidationErrors((ValidatableContract) value));

        } else if (isCollection) {
            validationErrors.addAll(getValidationErrors(field, (Collection<Object>) value));

        } else if (isArray) {
            validationErrors.addAll(getValidationErrors(field, (Object[]) value));

        } else {
            LOG.trace("Field {}: OK", field.getName());
        }

        return validationErrors;
    }

    private List<String> getValidationErrors(Field field, Collection<Object> value) {
        List<String> validationErrors = new ArrayList<>();

        boolean canBeEmpty = !field.isAnnotationPresent(NotEmpty.class);
        boolean isEmpty = value.isEmpty();

        if (!canBeEmpty && isEmpty) {
            LOG.trace("Field {}: Invalid - Empty collection", field.getName());
            validationErrors.add(String.format("Parameter '%s' can not be empty", field.getName()));

        } else {
            LOG.trace("Field {}:", field.getName());
            validationErrors.addAll(getValidationErrors(value));
        }

        return validationErrors;
    }

    private List<String> getValidationErrors(Field field, Object[] value) {
        List<String> validationErrors = new ArrayList<>();

        boolean canBeEmpty = !field.isAnnotationPresent(NotEmpty.class);
        boolean isEmpty = value.length <= 0;

        if (!canBeEmpty && isEmpty) {
            LOG.trace("Field {}: Invalid - Empty array", field.getName());
            validationErrors.add(String.format("Parameter '%s' can not be empty", field.getName()));

        } else {
            LOG.trace("Field {}:", field.getName());
            validationErrors.addAll(getValidationErrors(value));
        }

        return validationErrors;
    }

    private List<String> getValidationErrors(ValidatableContract contract) {
        List<String> validationErrors = new ArrayList<>();
        try {
            contract.validate();
        } catch (ContractValidationException exception) {
            validationErrors.addAll(exception.getValidationErrors());
        }
        return validationErrors;
    }

    private List<String> getValidationErrors(Collection<Object> contracts) {
        Sequence<Object> sequence = Sequence.of(contracts);
        Sequence<ValidatableContract> validatableContracts = getValidatableContracts(sequence);

        return getValidationErrors(validatableContracts);
    }

    private List<String> getValidationErrors(Object[] contracts) {
        Sequence<Object> sequence = Sequence.of(contracts);
        Sequence<ValidatableContract> validatableContracts = getValidatableContracts(sequence);

        return getValidationErrors(validatableContracts);
    }

    private List<String> getValidationErrors(Sequence<ValidatableContract> contracts) {
        List<String> validationErrors = new ArrayList<>();
        for (ValidatableContract contract : contracts) {
            validationErrors.addAll(getValidationErrors(contract));
        }
        return validationErrors;
    }

    private Sequence<ValidatableContract> getValidatableContracts(Sequence<Object> objects) {
        return objects.filter(object -> object instanceof ValidatableContract).cast(ValidatableContract.class);
    }
}