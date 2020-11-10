package com.getir.rig.exception.type;

import com.getir.rig.util.RigConstants;

import javax.persistence.EntityNotFoundException;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends EntityNotFoundException {
    public RecordNotFoundException(String s) {
        super(String.format("%s " + RigConstants.NOT_FOUND_DB, s));
    }
}
