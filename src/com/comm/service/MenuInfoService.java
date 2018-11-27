package com.comm.service;

import java.util.List;

import com.comm.model.MenuInfo;

public interface MenuInfoService {
    List<MenuInfo> getAllMenu();
    
    List<MenuInfo> getMenuBySeqs(String seqs);
    
    void addMenuInfo(MenuInfo menuInfo);
    
    void updateMenuInfo(MenuInfo menuInfo);
    
    void delMenuInfoBymenuId(String menuId);
    
    MenuInfo getmenuInfoBymenuId(String menuId);
    
    String menuInfodo(String json);
}
