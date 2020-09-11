package com.openpayd.ots.entity;

import com.openpayd.ots.entity.base.GenericEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="ADDRESS")
public class Address extends GenericEntity<Long> {

    @Column(name="INFO", length=50, nullable=false)
    private String info;

    @Column(name="CITY", nullable=false)
    private String city;

    @Column(name="COUNTRY", length=3, nullable=false)
    private String country;

}