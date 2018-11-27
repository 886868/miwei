package com.comm.dto;

import java.io.Serializable;

public class ADBRes implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Object ads = "";
    private String exception = "";
    public Object getAds() {
        return ads;
    }
    public void setAds(Object ads) {
        this.ads = ads;
    }
    public String getException() {
        return exception;
    }
    public void setException(String exception) {
        this.exception = exception;
    }
}
