package com.comm.dto;

import java.io.Serializable;

public class BookListRes implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer total = 0; 
    private Object bookList = "";
    private String exception= "";
    
    public BookListRes() {
    }
    
    public BookListRes(Integer total, Object bookList, String exception) {
        this.total = total;
        this.bookList = bookList;
        this.exception = exception;
    }
    
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Object getBookList() {
        return bookList;
    }
    public void setBookList(Object bookList) {
        this.bookList = bookList;
    }
    public String getException() {
        return exception;
    }
    public void setException(String exception) {
        this.exception = exception;
    }
}
