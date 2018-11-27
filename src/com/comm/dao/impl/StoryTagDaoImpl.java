package com.comm.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.StoryTagDao;
import com.comm.model.StoryTag;

@Repository
public class StoryTagDaoImpl extends ACommonDao<StoryTag> implements StoryTagDao {

    protected StoryTagDaoImpl(Class<StoryTag> entityClass) {
        super(entityClass);
    }
    
    public StoryTagDaoImpl() {
        super(StoryTag.class);
    }

    @Override
    public List<Map<String, Object>> getTagsByBookId(String bookId) {
        Session session = getCurrentSession();
        StringBuffer sql = new StringBuffer();
        
        sql.append("SELECT ")
        .append("st.tag_id AS tagId, ")
        .append("st.tag_name AS tagName, ")
        .append("st.tag_color AS tagColor ")
        .append("FROM ")
        .append("story_tag st, ")
        .append("story_tag_div std ")
        .append("WHERE ")
        .append("std.book_id=:bookId ")
        .append("AND std.tag_id = st.tag_id ");
        
        Query query = session.createSQLQuery(sql.toString());   
        query.setParameter("bookId", bookId);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> lst = query.list();
        
        return lst;
    }

    @Override
    public List<Map<String, Object>> getFirstTags() {
        Session session = getCurrentSession();
        StringBuffer hql = new StringBuffer();
        hql.append("select tagId as tagId, tagName as tagName, tagColor as tagColor from StoryTag where tagType='0'");
        
        Query query = session.createQuery(hql.toString());
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> lst = query.list();
        return lst;
        
    }

    @Override
    public List<Map<String, Object>> getTagsByFirst(String tagId) {
        Session session = getCurrentSession();
        StringBuffer hql = new StringBuffer();
        hql.append("select tagId as tagId, tagName as tagName, tagColor as tagColor  from StoryTag where parentTagId=:tagId");
        
        Query query = session.createQuery(hql.toString());
        query.setParameter("tagId", tagId);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> lst = query.list();
        return lst;
    }

}
