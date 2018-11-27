package com.comm.dao.impl;

import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.ScrollbarInfoDao;
import com.comm.model.ScrollbarInfo;

@Repository
public class ScrollbarInfoDaoImpl extends ACommonDao<ScrollbarInfo> implements ScrollbarInfoDao {

    protected ScrollbarInfoDaoImpl(Class<ScrollbarInfo> entityClass) {
        super(entityClass);
    }
    
    public ScrollbarInfoDaoImpl() {
        super(ScrollbarInfo.class);
    }

}
