package com.getir.rig.entity.base;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public interface GenericEntity<T extends Serializable> {
}
