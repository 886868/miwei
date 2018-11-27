package com.comm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="menu_info")
public class MenuInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private long seq;
    private String menuId = "";
    private String menuName = "";
    private String menuUrl = "";
    private String parent = "";
    private String node = "";
//    private String authority;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    @Column(name="menu_id")
    public String getmenuId() {
        return menuId;
    }

    public void setmenuId(String menuId) {
        this.menuId = menuId;
    }

    @Column(name="menu_name")
    public String getmenuName() {
        return menuName;
    }

    public void setmenuName(String menuName) {
        this.menuName = menuName;
    }

    @Column(name="menu_url")
    public String getmenuUrl() {
        return menuUrl;
    }

    public void setmenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

//    public String getAuthority() {
//        return authority;
//    }
//
//    public void setAuthority(String authority) {
//        this.authority = authority;
//    }

}
