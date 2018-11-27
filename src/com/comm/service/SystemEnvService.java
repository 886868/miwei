package com.comm.service;

import java.util.List;

import com.comm.model.SystemEnv;

public interface SystemEnvService {
    public SystemEnv getSystemEnv(String item);

    public List<SystemEnv> getAllSystemEnv();

    public String systemEnvdo(String json);
}
