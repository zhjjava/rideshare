package edu.mum.rideshare.validator;


import org.springframework.validation.Errors;

import edu.mum.rideshare.data.DemoModelData;
import edu.mum.rideshare.util.LcfValidationUtils;

public class DemoModelDataValidator extends LcfBaseValidator<DemoModelData>  {

    public void validate(Object obj, Errors errors) {
    	LcfValidationUtils.validateString(errors, "field1");
    	LcfValidationUtils.validateStringMinAndMaxLength(errors, "field2", 3, 6, false);
    	LcfValidationUtils.validateStringMinAndMaxLength(errors, "field3",  3, 6, true);
    	LcfValidationUtils.validateInteger(errors, "field4");
    	LcfValidationUtils.validateIntMinAndMax(errors, "field5", 0, 100);
    	
    	LcfValidationUtils.validateInteger(errors, "radioBoxValue");
    	LcfValidationUtils.validateString(errors, "selectedValue");
    	LcfValidationUtils.validateArray(errors, "checkedValues");
    	LcfValidationUtils.validateArray(errors, "checkedValues2",2,4);
    }
}