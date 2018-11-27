package com.comm.dao.impl;

import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.AdBannerDao;
import com.comm.model.AdBanner;

@Repository
public class AdBannerDaoImpl extends ACommonDao<AdBanner> implements AdBannerDao {

    protected AdBannerDaoImpl(Class<AdBanner> entityClass) {
        super(entityClass);
    }
    
    public AdBannerDaoImpl() {
        super(AdBanner.class);
    }

}
