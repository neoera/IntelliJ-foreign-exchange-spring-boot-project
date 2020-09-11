package com.openpayd.ots.exception;

import com.openpayd.ots.util.AtsConstants;

public class NotNullException extends RuntimeException {

  public NotNullException(String str) {
      super(String.format("%s " + AtsConstants.CANNOT_NULL,str));
  }
}
