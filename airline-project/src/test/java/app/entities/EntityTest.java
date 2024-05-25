package app.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

abstract public class EntityTest {

    public Set<ConstraintViolation<Object>> getSetOfViolation(Validator validator , Object object) {
        return validator.validate(object);
    }

    public boolean isSetWithViolationIsEmpty(Validator validator, Object object) {
        return getSetOfViolation(validator, object).isEmpty();
    }
}
