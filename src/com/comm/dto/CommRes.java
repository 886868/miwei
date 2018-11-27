package com.comm.dto;

import java.io.Serializable;

public class CommRes implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Object data = "";
    private String exception = "";
    
    public CommRes() {}
     
    public CommRes(Object data, String exception) {
        this.data = data;
        this.exception = exception;
    }
    
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public String getException() {
        return exception;
    }
    public void setException(String exception) {
        this.exception = exception;
    }
}
