package com.comm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="scrollbar_info")
public class ScrollbarInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 主键  uuid
    private String uuid = "";
    // 对象id    item_id
    private String itemId = "";
    // 对象类型    item_type
    private String itemType = "";
    // 对应业务    div_bz
    private String divBz = "";
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
    @Column(name="item_id")
    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    @Column(name="item_type")
    public String getItemType() {
        return itemType;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    @Column(name="div_bz")
    public String getDivBz() {
        return divBz;
    }
    public void setDivBz(String divBz) {
        this.divBz = divBz;
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
