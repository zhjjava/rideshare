package edu.mum.rideshare.validator;


import org.springframework.validation.Validator;

import edu.mum.core.util.GenericsUtils;

public abstract class LcfBaseValidator<T> implements Validator {

    public boolean supports(Class candidate) {
        return GenericsUtils.getSuperClassGenricType(getClass()).isAssignableFrom(candidate);
    }

}