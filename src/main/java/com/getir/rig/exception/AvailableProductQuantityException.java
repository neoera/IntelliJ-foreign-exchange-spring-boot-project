package com.getir.rig.exception;

public class AvailableProductQuantityException extends RuntimeException {
    public AvailableProductQuantityException(String s) {
        super(String.format("%s ", s));
    }
}
