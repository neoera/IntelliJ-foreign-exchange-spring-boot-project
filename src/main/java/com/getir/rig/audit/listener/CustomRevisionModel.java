package com.getir.rig.audit.listener;

import lombok.Data;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public class CustomRevisionModel implements Serializable {


    @Id
    @GeneratedValue
    @RevisionNumber
    @Column(name = "REV")
    private int id;

    @RevisionTimestamp
    @Column(name = "REVTSTMP")
    private long timestamp;

    @Transient
    public Date getRevisionDate() {
        return new Date(this.timestamp);
    }
}
