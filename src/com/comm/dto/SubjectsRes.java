package com.comm.dto;

import java.io.Serializable;

public class SubjectsRes implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Object subjects = "";
    private String exception = "";
    public Object getSubjects() {
        return subjects;
    }
    public void setSubjects(Object subjects) {
        this.subjects = subjects;
    }
    public String getException() {
        return exception;
    }
    public void setException(String exception) {
        this.exception = exception;
    }
}
