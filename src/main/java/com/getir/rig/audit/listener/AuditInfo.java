package com.getir.rig.audit.listener;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@RevisionEntity(CustomRevisionListener.class)
public class AuditInfo extends CustomRevisionModel{

    @Column(name = "USER_ID")
    private String userId;

}
