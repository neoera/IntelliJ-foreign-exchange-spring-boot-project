package com.openpayd.ots.exception;

public class DuplicateDataFoundException extends RuntimeException {

    public DuplicateDataFoundException(String str) {
        super(String.format("Duplicate data found with this client: %s", str));
    }
}
