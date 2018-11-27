package com.comm.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.StoryTagDivDao;
import com.comm.model.StoryTag;
import com.comm.model.StoryTagDiv;

@Repository
public class StoryTagDivDaoImpl extends ACommonDao<StoryTagDiv> implements StoryTagDivDao {

    protected StoryTagDivDaoImpl(Class<StoryTagDiv> entityClass) {
        super(entityClass);
    }
    
    public StoryTagDivDaoImpl() {
        super(StoryTagDiv.class);
    }

    @Override
    public List<StoryTag> getLstByBookId(String bookId) {
        Session session = getCurrentSession();
        StringBuffer sql = new StringBuffer();
        sql.append("select {st.*} ")
        .append("from story_tag st, story_tag_div std ")
        .append("where std.book_id=:bookId ")
        .append("and ")
        .append("std.tag_id=st.tag_id");
        Query query = session.createSQLQuery(sql.toString()).addEntity("st", StoryTag.class);   
        query.setParameter("bookId", bookId);
        @SuppressWarnings("unchecked")
        List<StoryTag> lst = query.list();
        return lst;
    }

}
