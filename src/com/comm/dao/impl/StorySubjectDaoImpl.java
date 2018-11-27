package com.comm.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.StorySubjectDao;
import com.comm.model.StorySubject;

@Repository
public class StorySubjectDaoImpl extends ACommonDao<StorySubject> implements StorySubjectDao {

    protected StorySubjectDaoImpl(Class<StorySubject> entityClass) {
        super(entityClass);
    }
    
    public StorySubjectDaoImpl() {
        super(StorySubject.class);
    }

    @Override
    public List<Map<String, Object>> getAllSubjects() {
        Session session = getCurrentSession();
        StringBuffer hql = new StringBuffer();
        hql.append("select subjectId as subjectId, subjectName as subjectName from StorySubject");
        
        Query query = session.createQuery(hql.toString());
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> lst = query.list();
        return lst;
    }

}
