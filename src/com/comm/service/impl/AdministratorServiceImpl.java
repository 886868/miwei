package com.comm.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.AdministratorDao;
import com.comm.model.Administrator;
import com.comm.service.AdministratorService;
import com.comm.util.HqlConst;

@Service
@Transactional
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorDao administratorDao;

    /**
     * 根据条件进行查询 adminId：管理员id name：名字 num：电话号码
     */
    @Override
    public Map<String, Object> getDetailsByAdminId(String adminId, String name, String num, String city, String rule, int start,
            int limit) {
        return administratorDao.getDetailsByAdminId(adminId, name, num, city, rule, start, limit);
    }

    /**
     * 增删改管理员
     * 
     * @author LiLiang
     * @param json
     *            增删改数据
     * @return 结果
     */
    @Override
    public String administratordo(String json) {
        return administratorDao.administratordo(json);
    }

    /**
     * 用户登录
     * 
     * @author guoyu
     * @param u
     * @return
     */
    @Override
    public boolean login(Administrator admin) {
        Administrator admini = administratorDao.queryByKeyAndValue(HqlConst.ADMIN_ID, admin.getAdminId());
        if (admini == null || !admini.getPassword().equals(admin.getPassword())) {
            return false;
        }
        return true;
    }

    @Override
    public void addAdmin(Administrator admin) {
        administratorDao.save(admin);
    }

    @Override
    public void updateAdmin(Administrator admin) {
        administratorDao.update(admin);
    }

    @Override
    public void delAdminBySeq(String adminId) {
        administratorDao.deleteByKeyAndValue(HqlConst.ADMIN_ID, adminId);
    }

    @Override
    public Administrator getAdmin(String adminId) {
        return administratorDao.queryByKeyAndValue(HqlConst.ADMIN_ID, adminId);
    }

}
