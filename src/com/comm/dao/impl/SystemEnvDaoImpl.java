package com.comm.dao.impl;

import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.SystemEnvDao;
import com.comm.model.SystemEnv;
import com.comm.util.HqlConst;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class SystemEnvDaoImpl extends ACommonDao<SystemEnv> implements SystemEnvDao {
   protected SystemEnvDaoImpl(Class<SystemEnv> entityClass) {
        super(entityClass);
    }
   
   public SystemEnvDaoImpl() {
       super(SystemEnv.class);
   }

   @Override
   public String systemEnvdo(String json) {
        
        JSONObject obj = JSONObject.fromObject(json);
        JSONArray insert = obj.getJSONArray("insert");
        JSONArray update = obj.getJSONArray("update");
        JSONArray delete = obj.getJSONArray("delete");
        if (null != insert) {
            for (int i = 0; i < insert.size(); i++) {
                SystemEnv systemEnv = new SystemEnv();
                JSONObject jsonobj = insert.getJSONObject(i);
                SystemEnv systemE = queryByKeyAndValue(HqlConst.ITEM, jsonobj.optString("key"));
                if (null == systemE) { // 没有数据 ，则 插入 数据
                    systemEnv.setItem(jsonobj.optString("key"));
                    systemEnv.setValue(jsonobj.getString("value"));
                    save(systemEnv);
                } else {
                    return "fail";
                }
            }
        }
        if (null != update) {
            for (int i = 0; i < update.size(); i++) {
                SystemEnv systemEnvUpd = new SystemEnv();
                JSONObject jsonobj = update.getJSONObject(i);
                systemEnvUpd = queryByKeyAndValue(HqlConst.ITEM, jsonobj.optString("key")); 
                if (null != systemEnvUpd) {
                    systemEnvUpd.setValue(jsonobj.getString("value"));
                    update(systemEnvUpd);
                }
            }
        }
        if (null != delete) {
            for (int i = 0; i < delete.size(); i++) {
                JSONObject jsonobj = delete.getJSONObject(i);
                deleteByKeyAndValue(HqlConst.ITEM, jsonobj.optString("key"));
            }
        }
        return "";
    }
}
