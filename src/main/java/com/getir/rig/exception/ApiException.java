package com.getir.rig.exception;

class ApiException extends Exception{
  ApiException(int code, String msg) {
    super(msg);
  }
}
