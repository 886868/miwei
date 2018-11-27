package com.comm.dto;

import java.io.Serializable;

/**
 * 发送邮件需要使用的基本信息
 */

public class MailInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 邮件发送者的地址
    private String fromAddress;
    // 邮件接收者的地址
    private String toAddress;

    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;
    // 邮件附件的文件名
    private String[] attachFileNames;
    private boolean isHtmlFmt = false;

    public MailInfo() {
    }

    public MailInfo(String fromAddress, String toAddress, String subject, String content, String[] fileNames,
            boolean isHtml) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.subject = subject;
        this.content = content;
        this.attachFileNames = fileNames;
        this.isHtmlFmt = isHtml;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public boolean isHtmlFormat() {
        return isHtmlFmt;
    }

    /**
     * @param fromAddress
     *            the fromAddress to set
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    /**
     * @param toAddress
     *            the toAddress to set
     */
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
}
