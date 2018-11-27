package com.comm.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.MenuInfoDao;
import com.comm.model.MenuInfo;
import com.comm.util.HqlConst;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class MenuInfoDaoImpl extends ACommonDao<MenuInfo> implements MenuInfoDao {

    protected MenuInfoDaoImpl(Class<MenuInfo> entityClass) {
        super(entityClass);
    }

    public MenuInfoDaoImpl() {
        super(MenuInfo.class);
    }

    @Override
    public List<MenuInfo> getMenuBySeqs(String seqs) {
        Session session = getCurrentSession();
        String where = "";
        if (null != seqs && !"".equals(seqs)){
            where = " where seq in ("+seqs+") ";
        }
        String orderBy = " order by seq";
        Query query = session.createQuery("from MenuInfo"+where+orderBy);   
        List<MenuInfo> list= query.list();
        if (list.size() > 0){
            return list;
        }
        return null;
    }

    @Override
    public String menuInfodo(String json) {
        MenuInfo menuInfo = new MenuInfo(); 
        JSONObject obj = JSONObject.fromObject(json);
        JSONArray insert = obj.getJSONArray("insert");
        JSONArray update = obj.getJSONArray("update");
        JSONArray delete = obj.getJSONArray("delete");      
        if(null!=insert){
            for (int i = 0 ; i < insert.size(); i++) 
            {               
                JSONObject jsonobj  = (JSONObject) insert.get(i);
                MenuInfo menu = queryByKeyAndValue(HqlConst.MENU_ID, jsonobj.getString("menuId"));
                if (null == menu){  //没有数据 ，则 插入 数据
                    menuInfo.setmenuId(jsonobj.getString("menuId"));
                    menuInfo.setmenuName(jsonobj.getString("menuName"));
                    menuInfo.setmenuUrl(jsonobj.getString("menuUrl"));
                    menuInfo.setParent(jsonobj.getString("parent"));
                    menuInfo.setNode(jsonobj.getString("node"));
//                    menuInfo.setAuthority(jsonobj.getString("authority"));
                    save(menuInfo); 
                }else{
                    return "fail";
                }               
            }
        }
        if(null!=update){
            for (int i = 0 ; i < update.size(); i++) 
            {
                JSONObject jsonobj  = (JSONObject) update.get(i);
                menuInfo = queryByKeyAndValue(HqlConst.MENU_ID, jsonobj.getString("menuId"));
                if (null != menuInfo){
                    menuInfo.setmenuId(jsonobj.getString("menuId"));
                    menuInfo.setmenuName(jsonobj.getString("menuName"));
                    menuInfo.setmenuUrl(jsonobj.getString("menuUrl"));
                    menuInfo.setParent(jsonobj.getString("parent"));
                    menuInfo.setNode(jsonobj.getString("node"));
//                    menuInfo.setAuthority(jsonobj.getString("authority"));      
                    update(menuInfo);
                }               
            }
        }
        if(null!=delete){
            for (int i = 0 ; i < delete.size(); i++) 
            {
                JSONObject jsonobj  = (JSONObject) delete.get(i);   
                deleteByKeyAndValue(HqlConst.MENU_ID, jsonobj.getString("menuId"));
            }
        }
        return "";
    }
}
