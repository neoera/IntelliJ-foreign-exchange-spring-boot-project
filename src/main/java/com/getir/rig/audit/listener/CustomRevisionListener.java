package com.getir.rig.audit.listener;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        AuditInfo auditEnversInfo = (AuditInfo) revisionEntity;
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        auditEnversInfo.setUserId(userName);
    }
}
