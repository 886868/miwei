package com.comm.dto;

import java.io.Serializable;

public class TagsRes implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String parentId = "";
    private Object tags = "";
    private String exception = "";
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
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
