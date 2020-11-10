package com.getir.rig.exception.type;

import com.getir.rig.util.RigConstants;

public class NotCompatibleException extends RuntimeException {

  public NotCompatibleException(int code) {
      super(String.format("Id %d " + RigConstants.NOT_COMPATIBLE, code));
  }
}
