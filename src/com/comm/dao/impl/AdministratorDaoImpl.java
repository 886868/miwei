package com.comm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.AdministratorDao;
import com.comm.model.Administrator;
import com.comm.util.HqlConst;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class AdministratorDaoImpl extends ACommonDao<Administrator> implements AdministratorDao {
    
    static Logger logger = Logger.getLogger(AdministratorDaoImpl.class);
    
    protected AdministratorDaoImpl(Class<Administrator> entityClass) {
        super(entityClass);
    }
    
    protected AdministratorDaoImpl() {
        super(Administrator.class);
    }

    /**
     * 根据条件查询
     * 
     * @author LiLiang
     * @param adminId
     *            管理员id
     * @param name
     *            管理员名称
     * @param num
     *            手机号
     * @param start
     *            开始页数
     * @param limit
     *            每页长度
     */
    @Override
    public Map<String, Object> getDetailsByAdminId(String adminId, String name, String num, String city, String rule, int start,
            int limit) {
        Session session = getCurrentSession();
        StringBuffer sb = new StringBuffer();
        sb.append("from Administrator as r where 1=1");
        if (!(adminId == null || "".equals(adminId))) {
            sb.append(" and r.adminId='" + adminId + "'");
        }
        if (!(name == null || "".equals(name))) {
            sb.append(" and r.name='" + name + "'");
        }
        if (!(num == null || "".equals(num))) {
            sb.append(" and r.num='" + num + "'");
        }
        if (!(city == null || "".equals(city))) {
            sb.append(" and r.city='" + city + "'");
        }
        if (!rule.equals("99")) {
            sb.append(" and r.rule <> '99' ");
        }
        Query query = session.createQuery(sb.toString()).setFirstResult(start).setMaxResults(limit);

        Query queryTotal = session.createQuery(sb.toString());
        int total = queryTotal.list().size();
        List<Administrator> list = query.list();
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("list", list);
        hm.put("total", total);
        return hm;
    }

    /**
     * 根据条件增删改
     * 
     * @author LiLiang
     * @param json
     *            增删改内容
     */
    @Override
    public String administratordo(String json) {
        Administrator administrator = new Administrator();
        JSONObject obj = JSONObject.fromObject(json);
        JSONArray insert = obj.getJSONArray("insert");
        JSONArray update = obj.getJSONArray("update");
        JSONArray delete = obj.getJSONArray("delete");
        if (null != insert) {
            for (int i = 0; i < insert.size(); i++) {
                JSONObject jsonobj = (JSONObject) insert.get(i);
                Administrator adm = queryByKeyAndValue(HqlConst.ADMIN_ID, jsonobj.getString("adminId"));
                if (null == adm) {
                    administrator = new Administrator();
                    administrator.setAdminId(jsonobj.getString("adminId"));
                    administrator.setName(jsonobj.getString("name"));
                    administrator.setNum(jsonobj.getString("num"));
//                    administrator.setCity(jsonobj.getString("city"));
                    administrator.setRule(jsonobj.getString("rule"));
                    administrator.setPassword(jsonobj.getString("pwd"));
                    save(administrator);
                } else {
                    return "fail";
                }
            }
        }
        if (null != update) {
            for (int i = 0; i < update.size(); i++) {
                JSONObject jsonobj = (JSONObject) update.get(i);
                administrator = queryByKeyAndValue(HqlConst.ADMIN_ID, jsonobj.getString("adminId"));
                if (null != administrator) {
                    administrator.setAdminId(jsonobj.getString("adminId"));
                    administrator.setName(jsonobj.getString("name"));
                    administrator.setNum(jsonobj.getString("num"));
//                    administrator.setCity(jsonobj.getString("city"));
                    administrator.setRule(jsonobj.getString("rule"));
                    update(administrator);
                }
            }
        }
        if (null != delete) {
            for (int i = 0; i < delete.size(); i++) {
                JSONObject jsonobj = (JSONObject) delete.get(i);
                administrator = new Administrator();
                deleteByKeyAndValue(HqlConst.ADMIN_ID, jsonobj.getString("adminId"));
            }
        }
        return "";
    }

}
