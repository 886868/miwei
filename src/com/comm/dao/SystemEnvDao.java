package com.comm.dao;

import com.comm.model.SystemEnv;

public interface SystemEnvDao extends ICommonDao<SystemEnv> {
    public String systemEnvdo(String json);
}
