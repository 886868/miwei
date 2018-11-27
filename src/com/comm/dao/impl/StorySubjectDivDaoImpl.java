package com.comm.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.StorySubjectDivDao;
import com.comm.model.StorySubject;
import com.comm.model.StorySubjectDiv;

@Repository
public class StorySubjectDivDaoImpl extends ACommonDao<StorySubjectDiv> implements StorySubjectDivDao {

    protected StorySubjectDivDaoImpl(Class<StorySubjectDiv> entityClass) {
        super(entityClass);
    }
    
    public StorySubjectDivDaoImpl() {
        super(StorySubjectDiv.class);
    }

    @Override
    public List<StorySubject> getLstByBookId(String bookId) {
        Session session = getCurrentSession();
        StringBuffer sql = new StringBuffer();
        
        sql.append("select {ss.*} ")
         .append("from story_subject ss, story_subject_div ssd ")
         .append("where ")
         .append("ssd.book_id=:bookId ")
         .append("and ")
         .append("ssd.subject_id=ss.subject_id");
        
        Query query = session.createSQLQuery(sql.toString()).addEntity("ss", StorySubject.class);   
        query.setParameter("bookId", bookId);
        @SuppressWarnings("unchecked")
        List<StorySubject> lst = query.list();
        return lst;
    }

}
