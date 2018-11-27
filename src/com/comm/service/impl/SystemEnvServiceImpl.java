package com.comm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.SystemEnvDao;
import com.comm.model.SystemEnv;
import com.comm.service.SystemEnvService;
import com.comm.util.HqlConst;

@Service
@Transactional
public class SystemEnvServiceImpl implements SystemEnvService {

    @Autowired
    private SystemEnvDao systemEnvDao;

    @Override
    public SystemEnv getSystemEnv(String item) {
        return systemEnvDao.queryByKeyAndValue(HqlConst.ITEM, item);
    }

    @Override
    public List<SystemEnv> getAllSystemEnv() {
        return systemEnvDao.listAll();
    }

    @Override
    public String systemEnvdo(String json)  {
        return systemEnvDao.systemEnvdo(json);
    }
}
