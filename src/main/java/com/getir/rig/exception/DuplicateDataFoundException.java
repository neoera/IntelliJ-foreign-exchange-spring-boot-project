package com.getir.rig.exception;

public class DuplicateDataFoundException extends RuntimeException {

    public DuplicateDataFoundException(String str) {
        super(String.format("Duplicate data found with this order: %s", str));
    }
}
