package com.openpayd.fex.entity.base;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class GenericEntity<I extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genericSequence")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }
}
