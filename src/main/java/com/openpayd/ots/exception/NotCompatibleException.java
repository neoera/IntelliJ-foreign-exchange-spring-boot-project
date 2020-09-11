package com.openpayd.ots.exception;

import com.openpayd.ots.util.AtsConstants;

public class NotCompatibleException extends RuntimeException {

  public NotCompatibleException(int code) {
      super(String.format("Id %d " + AtsConstants.NOT_COMPATIBLE, code));
  }
}
