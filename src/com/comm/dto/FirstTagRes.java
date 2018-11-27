package com.comm.dto;

import java.io.Serializable;

public class FirstTagRes implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Object tags = "";
    private String exception = "";
    public Object getTags() {
        return tags;
    }
    public void setTags(Object tags) {
        this.tags = tags;
    }
    public String getException() {
        return exception;
    }
    public void setException(String exception) {
        this.exception = exception;
    }
}
