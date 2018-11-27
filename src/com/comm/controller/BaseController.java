package com.comm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.comm.dto.CommRes;
import com.comm.util.ExceptionUtil;

@RequestMapping(produces = "application/json; charset=utf-8")
public abstract class BaseController {

    /**
     * instances of the log class
     */
    private static Logger logger = Logger.getLogger(BaseController.class);

    public BaseController() {
    }

    /** 基于@ExceptionHandler异常处理 */
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommRes exp(HttpServletRequest request, Exception ex, HttpServletResponse response) {
        logger.error("Request: " + request.getRequestURL() + " raised " + ExceptionUtil.getExceptionInfo(ex));
        return new CommRes("", "系统异常");
    }
}
