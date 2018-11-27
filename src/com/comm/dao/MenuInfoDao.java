package com.comm.dao;

import java.util.List;

import com.comm.model.MenuInfo;

public interface MenuInfoDao extends ICommonDao<MenuInfo> {
    List<MenuInfo> getMenuBySeqs(String seqs);
    String menuInfodo(String json);
}
