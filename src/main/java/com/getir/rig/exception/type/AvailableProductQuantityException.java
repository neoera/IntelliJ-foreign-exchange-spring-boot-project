package com.getir.rig.exception.type;

public class AvailableProductQuantityException extends RuntimeException {
    public AvailableProductQuantityException(String s) {
        super(String.format("%s ", s));
    }
}
