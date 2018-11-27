package com.comm.dto;

import java.io.Serializable;

public class BKCommRes implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String exception = "";
    
    public BKCommRes() {
    }
    
    public BKCommRes(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
