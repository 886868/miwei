package com.comm.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.StoryDirInfoDao;
import com.comm.model.StoryDirInfo;

@Repository
public class StoryDirInfoDaoImpl extends ACommonDao<StoryDirInfo> implements StoryDirInfoDao {

    protected StoryDirInfoDaoImpl(Class<StoryDirInfo> entityClass) {
        super(entityClass);
    }
    
    public StoryDirInfoDaoImpl() {
        super(StoryDirInfo.class);
    }

    @Override
    public List<Map<String, Object>> getLstByBookId(String bookId) {
        Session session = getCurrentSession();
        StringBuffer hql = new StringBuffer();
        hql.append("select chTitle as chTitle, chLink as chLink, chNo as chNo from StoryDirInfo where bookId=:bookId order by chNo");
        
        Query query = session.createQuery(hql.toString());
        query.setParameter("bookId", bookId);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> lst = query.list();
        
        return lst;
    }

}
