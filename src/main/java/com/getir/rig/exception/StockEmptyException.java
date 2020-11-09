package com.getir.rig.exception;

public class StockEmptyException extends RuntimeException {
    public StockEmptyException(String s) {
        super(String.format("%s ", s));
    }
}
