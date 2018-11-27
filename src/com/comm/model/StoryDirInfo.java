package com.comm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="story_dir_info")
public class StoryDirInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 主键  uuid
    private String uuid = "";
    // 图书id    book_id
    private String bookId = "";
    // 章节标题    ch_title
    private String chTitle = "";
    // 章节内容链接  ch_link
    private String chLink = "";
    // 章节数字 ch_no
    private Integer chNo = 0;
    // 创建时间    cr_date
    private String crDate = "";
    // 更新时间    upd_date
    private String updDate = "";
    
    @Id
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    @Column(name="book_id")
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    @Column(name="ch_title")
    public String getChTitle() {
        return chTitle;
    }
    public void setChTitle(String chTitle) {
        this.chTitle = chTitle;
    }
    @Column(name="ch_link")
    public String getChLink() {
        return chLink;
    }
    public void setChLink(String chLink) {
        this.chLink = chLink;
    }
    @Column(name="ch_no")
    public Integer getChNo() {
        return chNo;
    }
    public void setChNo(Integer chNo) {
        this.chNo = chNo;
    }
    @Column(name="cr_date")
    public String getCrDate() {
        return crDate;
    }
    public void setCrDate(String crDate) {
        this.crDate = crDate;
    }
    @Column(name="upd_date")
    public String getUpdDate() {
        return updDate;
    }
    public void setUpdDate(String updDate) {
        this.updDate = updDate;
    }
}
