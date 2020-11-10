package com.getir.rig.exception.type;

public class StockEmptyException extends RuntimeException {
    public StockEmptyException(String s) {
        super(String.format("%s ", s));
    }
}
