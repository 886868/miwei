package com.comm.dao.impl;

import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.AdInfoDao;
import com.comm.model.AdInfo;

@Repository
public class AdInfoDaoImpl extends ACommonDao<AdInfo> implements AdInfoDao {

    protected AdInfoDaoImpl(Class<AdInfo> entityClass) {
        super(entityClass);
    }
    
    public AdInfoDaoImpl() {
        super(AdInfo.class);
    }

}
