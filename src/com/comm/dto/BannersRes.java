package com.comm.dto;

import java.io.Serializable;

public class BannersRes implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Object banners = "";
    private String exception = "";
    public Object getBanners() {
        return banners;
    }
    public void setBanners(Object banners) {
        this.banners = banners;
    }
    public String getException() {
        return exception;
    }
    public void setException(String exception) {
        this.exception = exception;
    }
}
