package com.openpayd.ots.exception;

import com.openpayd.ots.util.AtsConstants;

import javax.persistence.EntityNotFoundException;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends EntityNotFoundException {
    public RecordNotFoundException(String s) {
        super(String.format("%s " + AtsConstants.NOT_FOUND_DB, s));
    }
}
