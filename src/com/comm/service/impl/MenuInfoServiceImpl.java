package com.comm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.MenuInfoDao;
import com.comm.model.MenuInfo;
import com.comm.service.MenuInfoService;
import com.comm.util.HqlConst;

@Service
@Transactional
public class MenuInfoServiceImpl implements MenuInfoService {
    
    @Autowired
    private MenuInfoDao menuInfoDao;

    @Override
    public List<MenuInfo> getAllMenu() {
        return menuInfoDao.listAll();
    }

    @Override
    public List<MenuInfo> getMenuBySeqs(String seqs) {
        return menuInfoDao.getMenuBySeqs(seqs);
    }

    @Override
    public void addMenuInfo(MenuInfo menuInfo) {
        menuInfoDao.save(menuInfo);
    }

    @Override
    public void updateMenuInfo(MenuInfo menuInfo) {
        menuInfoDao.update(menuInfo);
    }

    @Override
    public void delMenuInfoBymenuId(String menuId) {
        menuInfoDao.deleteByKeyAndValue(HqlConst.MENU_ID, menuId);
    }

    @Override
    public MenuInfo getmenuInfoBymenuId(String menuId) {
        return menuInfoDao.queryByKeyAndValue(HqlConst.MENU_ID, menuId);
    }

    @Override
    public String menuInfodo(String json) {
        return menuInfoDao.menuInfodo(json);
    }
    
}
