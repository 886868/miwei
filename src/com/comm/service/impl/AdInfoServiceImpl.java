package com.comm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.AdInfoDao;
import com.comm.service.AdInfoService;

@Service
@Transactional
public class AdInfoServiceImpl implements AdInfoService {
    @Autowired
    private AdInfoDao adInfoDao;
}
