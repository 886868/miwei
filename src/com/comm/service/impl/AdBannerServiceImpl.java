package com.comm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.AdBannerDao;
import com.comm.model.AdBanner;
import com.comm.service.AdBannerService;

@Service
@Transactional
public class AdBannerServiceImpl implements AdBannerService {
    
    @Autowired
    private AdBannerDao adBannerDao;

    @Override
    public List<AdBanner> getAdBanners() {
        return adBannerDao.listAll();
    }
}
