package com.getir.rig.exception.type;

class ApiException extends Exception{
  ApiException(int code, String msg) {
    super(msg);
  }
}
