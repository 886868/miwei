package com.comm.dto;

import java.io.Serializable;

public class ChatpersRes implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Object chapters = "";
    private String exception = "";
    public Object getChapters() {
        return chapters;
    }
    public void setChapters(Object chapters) {
        this.chapters = chapters;
    }
    public String getException() {
        return exception;
    }
    public void setException(String exception) {
        this.exception = exception;
    }
    
}
