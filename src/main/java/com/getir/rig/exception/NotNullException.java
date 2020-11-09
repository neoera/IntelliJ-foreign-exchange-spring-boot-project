package com.getir.rig.exception;

import com.getir.rig.util.RigConstants;

public class NotNullException extends RuntimeException {

  public NotNullException(String str) {
      super(String.format("%s " + RigConstants.CANNOT_NULL,str));
  }
}
