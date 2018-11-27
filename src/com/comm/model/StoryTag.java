package com.comm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="story_tag")
public class StoryTag implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 标签id    tag_id
    private String tagId = "";
    // 标签名称    tag_name
    private String tagName = "";
    // 标签类型    tag_type
    private String tagType = "";
    // 父标签id   parent_tag_id
    private String parentTagId = "";
    // 标签色值    tag_color
    private String tagColor = "";
    // 创建时间    cr_date
    private String crDate = "";
    // 更新时间    upd_date
    private String updDate = "";
    
    @Id
    @Column(name="tag_id")
    public String getTagId() {
        return tagId;
    }
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
    @Column(name="tag_name")
    public String getTagName() {
        return tagName;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    @Column(name="tag_type")
    public String getTagType() {
        return tagType;
    }
    public void setTagType(String tagType) {
        this.tagType = tagType;
    }
    @Column(name="parent_tag_id")
    public String getParentTagId() {
        return parentTagId;
    }
    public void setParentTagId(String parentTagId) {
        this.parentTagId = parentTagId;
    }
    @Column(name="tag_color")
    public String getTagColor() {
        return tagColor;
    }
    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
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
