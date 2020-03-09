package com.openpayd.fex.exception;

class ApiException extends Exception{
  ApiException(int code, String msg) {
    super(msg);
  }
}
