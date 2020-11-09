package com.getir.rig.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ValidationInfo {
    private String type;
    private String message;
}
