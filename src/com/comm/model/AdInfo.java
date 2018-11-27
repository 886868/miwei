package com.comm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ad_info")
public class AdInfo implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 主键  ad_id
    private String adId = "";
    // 广告链接    ad_url
    private String adUrl = "";
    // 广告滚动图片  ad_scbar_url
    private String adScbarUrl = "";
    // 广告title ad_title
    private String adTitle = "";
    // 创建时间    cr_date
    private String crDate = "";
    // 更新时间    upd_date
    private String updDate = "";
    // bak1    bak1
    private String bak1 = "";
    // bak2    bak2
    private String bak2 = "";
    // bak3    bak3
    private String bak3 = "";
    // bak4    bak4
    private String bak4 = "";
    // bak5    bak5
    private String bak5 = "";
    
    @Id
    @Column(name="ad_id")
    public String getAdId() {
        return adId;
    }
    public void setAdId(String adId) {
        this.adId = adId;
    }
    @Column(name="ad_url")
    public String getAdUrl() {
        return adUrl;
    }
    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }
    @Column(name="ad_scbar_url")
    public String getAdScbarUrl() {
        return adScbarUrl;
    }
    public void setAdScbarUrl(String adScbarUrl) {
        this.adScbarUrl = adScbarUrl;
    }
    @Column(name="ad_title")
    public String getAdTitle() {
        return adTitle;
    }
    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
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
    public String getBak1() {
        return bak1;
    }
    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }
    public String getBak2() {
        return bak2;
    }
    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }
    public String getBak3() {
        return bak3;
    }
    public void setBak3(String bak3) {
        this.bak3 = bak3;
    }
    public String getBak4() {
        return bak4;
    }
    public void setBak4(String bak4) {
        this.bak4 = bak4;
    }
    public String getBak5() {
        return bak5;
    }
    public void setBak5(String bak5) {
        this.bak5 = bak5;
    }
}
