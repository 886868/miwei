package com.comm.dao;

import java.util.Map;

import com.comm.model.Administrator;

public interface AdministratorDao extends ICommonDao<Administrator> {
    public Map<String, Object> getDetailsByAdminId(String adminId, String name, String num, String city, String rule, int start,
            int limit);

    public String administratordo(String json);

}
