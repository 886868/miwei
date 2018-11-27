package com.comm.dto;

/**
 * 通用返回响应Bean
 * 
 * @author QuSheng
 */
public class ResponseBean {
    private Integer code;
    private Object data = "";
    private String error = "";
    
    public ResponseBean() {
        super();
    }
    
    public ResponseBean(Integer code, Object data, String error) {
        this.code = code;
        this.data = data;
        this.error = error;
    }
    
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    
    
}
