package com.comm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ad_banner")
public class AdBanner implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 主键  ad_b_id
    private String adBId = "";
    // 是否播放引导页广告   is_play_ad
    private String isPlayAd = "";
    // 播放广告的时长 ad_time
    private int adTime;
    // 广告图片的地址 ad_picutre_url
    private String adPicutreUrl = "";
    // 广告跳转    ad_url
    private String adUrl = "";
    // 创建时间    cr_date
    private String crDate = "";
    // 更新时间    upd_date
    private String updDate = "";
    
    @Id
    @Column(name="ad_b_id")
    public String getAdBId() {
        return adBId;
    }
    public void setAdBId(String adBId) {
        this.adBId = adBId;
    }
    @Column(name="is_play_ad")
    public String getIsPlayAd() {
        return isPlayAd;
    }
    public void setIsPlayAd(String isPlayAd) {
        this.isPlayAd = isPlayAd;
    }
    @Column(name="ad_time")
    public int getAdTime() {
        return adTime;
    }
    public void setAdTime(int adTime) {
        this.adTime = adTime;
    }
    @Column(name="ad_picutre_url")
    public String getAdPicutreUrl() {
        return adPicutreUrl;
    }
    public void setAdPicutreUrl(String adPicutreUrl) {
        this.adPicutreUrl = adPicutreUrl;
    }
    @Column(name="ad_url")
    public String getAdUrl() {
        return adUrl;
    }
    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
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
