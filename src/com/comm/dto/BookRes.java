package com.comm.dto;

import java.io.Serializable;

public class BookRes implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Object book = "";
    private String exception = "";
    public Object getBook() {
        return book;
    }
    public void setBook(Object book) {
        this.book = book;
    }
    public String getException() {
        return exception;
    }
    public void setException(String exception) {
        this.exception = exception;
    }
}
