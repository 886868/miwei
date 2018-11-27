package com.comm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="administrator")
public class Administrator implements Serializable {

    private static final long serialVersionUID = 1L;
    // 登录名
    private String adminId = "";
    // 姓名
    private String name = "";
    // 手机
    private String num = "";
    // 角色
    private String rule = "1";
    // 密码
    private String password = "";
    // 菜单列表
    private String menulist = "";
    // 主画面
    private long mainmenuseq;

    @Id
    @Column(name="admin_id")
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMenulist() {
        return menulist;
    }

    public void setMenulist(String menulist) {
        this.menulist = menulist;
    }

    public long getMainmenuseq() {
        return mainmenuseq;
    }

    public void setMainmenuseq(long mainmenuseq) {
        this.mainmenuseq = mainmenuseq;
    }

}
