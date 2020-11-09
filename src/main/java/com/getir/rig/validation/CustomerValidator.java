package com.getir.rig.validation;

import com.getir.rig.logger.RigLogger;
import com.getir.rig.util.RigValidationEnum;
import com.getir.rig.util.Utils;
import com.getir.rig.validation.annotation.RigValidationComponent;
import com.getir.rig.viewobject.CustomerRequest;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import java.util.Map;


public class CustomerValidator {

    private static RigLogger logger = (RigLogger) LoggerFactory.getLogger(CustomerValidator.class);
    private CustomerValidator() { throw new IllegalStateException("CustomerValidator IllegalStateException error "); }

    @RigValidationComponent
    public static class CreateCustomer implements Validator {

        @Override
        public boolean supports(Class<?> clazz) {return CustomerRequest.class.equals(clazz);}

        @Override
        public void validate(Object target, Errors errors) {
            MapBindingResult mapBindingResult = (MapBindingResult) errors;

            //get method parameters
            //Map<?, ?> parameters = mapBindingResult.getTargetMap();
            CustomerRequest customerRequest = (CustomerRequest) target;

            logger.info("customer request object: {}", customerRequest);

            if (Utils.isStringNullOrEmpty(customerRequest.getName())){
                mapBindingResult.reject(RigValidationEnum.CUSTOMER_NAME_CAN_NOT_BE_EMPTY.getKey(), RigValidationEnum.CUSTOMER_NAME_CAN_NOT_BE_EMPTY.getMessage());
            }

            if (Utils.isStringNullOrEmpty(customerRequest.getSurname())){
                mapBindingResult.reject(RigValidationEnum.CUSTOMER_SURNAME_CAN_NOT_BE_EMPTY.getKey(), RigValidationEnum.CUSTOMER_SURNAME_CAN_NOT_BE_EMPTY.getMessage());
            }

            if (Utils.isStringNullOrEmpty(customerRequest.getEmail())){
                mapBindingResult.reject(RigValidationEnum.CUSTOMER_EMAIL_CAN_NOT_BE_EMPTY.getKey(), RigValidationEnum.CUSTOMER_EMAIL_CAN_NOT_BE_EMPTY.getMessage());
            }

            if (Utils.isValidPhoneNumber(customerRequest.getPhone())){
                mapBindingResult.reject(RigValidationEnum.CUSTOMER_PHONE_NOT_VALID.getKey(), RigValidationEnum.CUSTOMER_PHONE_NOT_VALID.getMessage());
            }

        }
    }

    @RigValidationComponent
    public static class ListCustomer implements Validator {

        @Override
        public boolean supports(Class<?> clazz) {return Long.class.equals(clazz);}

        @Override
        public void validate(Object target, Errors errors) {

        }
    }
}
