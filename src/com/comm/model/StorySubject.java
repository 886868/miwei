package com.comm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="story_subject")
public class StorySubject implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 主键  seq
    private Integer seq = 0;
    // 主题id    subject_id
    private String subjectId = "";
    // 主题名称    subject_name
    private String subjectName = "";
    // 创建时间    cr_date
    private String crDate = "";
    // s更新时间    upd_date
    private String updDate = "";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getSeq() {
        return seq;
    }
    public void setSeq(int seq) {
        this.seq = seq;
    }
    @Column(name="subject_id")
    public String getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
    @Column(name="subject_name")
    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
