package com.comm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="story_subject_div")
public class StorySubjectDiv implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 主键  uuid
    private String uuid = "";
    // 主题id    subject_id
    private String subjectId = "";
    // 书编号 book_id
    private String bookId = "";
    // 排序字段 book_sort
    private String bookSort = "";
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
    @Column(name="subject_id")
    public String getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
    @Column(name="book_id")
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    @Column(name="book_sort")
    public String getBookSort() {
        return bookSort;
    }
    public void setBookSort(String bookSort) {
        this.bookSort = bookSort;
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
