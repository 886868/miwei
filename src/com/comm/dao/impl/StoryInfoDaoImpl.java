package com.comm.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.comm.dao.ACommonDao;
import com.comm.dao.StoryInfoDao;
import com.comm.model.StoryInfo;
import com.comm.util.DateUtil;
import com.comm.util.HqlConst;
import com.comm.util.StringUtil;

@Repository
public class StoryInfoDaoImpl extends ACommonDao<StoryInfo> implements StoryInfoDao {

    protected StoryInfoDaoImpl(Class<StoryInfo> entityClass) {
        super(entityClass);
    }
    
    public StoryInfoDaoImpl() {
        super(StoryInfo.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StoryInfo> getLstBySubject(String subject, String sortKey, String duration
            ,int start, int length) {
        Session session = getCurrentSession();
        StringBuffer dur = new StringBuffer();
        StringBuffer sql = new StringBuffer();
        
        sql.append("SELECT ")
        .append("{si.*} ")
        .append("FROM ")
        .append("story_info si, ")
        .append("story_subject_div ssd ")
        .append("WHERE ")
        .append("si.book_id = ssd.book_id ");
        if(!StringUtil.isEmpty(subject)){
            sql.append(" AND ")
            .append("ssd.subject_id=:subject ");
        }
        if(!StringUtil.isEmpty(duration)) {
            sql.append(" AND ")
            .append("si.upd_date >= :duration ");
            
            dur.append(DateUtil.getSomeDay(duration, DateUtil.sdf8, -7));
            dur.append(DateUtil.STARTTIME_HHmmssSSS);
        }
        
        if(!StringUtil.isEmpty(sortKey)) {
            sql.append("ORDER BY ");
            if(HqlConst.CR_DATE.equals(sortKey)){
                sql.append("si." + sortKey)
                .append(" DESC");
            } else {
                sql.append(sortKey);
            }
        }
        Query query = session.createSQLQuery(sql.toString()).addEntity("si", StoryInfo.class);
        
        if(!StringUtil.isEmpty(duration)) {
            query.setParameter("duration", dur.toString());
        }
        if(!StringUtil.isEmpty(subject)){
            query.setParameter("subject", subject);
        }
        
        if(start > -1) {
            query.setFirstResult(start);
        }
        if(length > -1) {
            query.setMaxResults(length);
        }
        
        return query.list();
    }

    @Override
    public int getCntBySubject(String subject, String sortKey, String duration) {
        Session session = getCurrentSession();
        StringBuffer dur = new StringBuffer();
        StringBuffer sql = new StringBuffer();
        
        sql.append("SELECT ")
        .append("count(*) ")
        .append("FROM ")
        .append("story_info si, ")
        .append("story_subject_div ssd ")
        .append("WHERE ")
        .append("si.book_id = ssd.book_id ");
        if(!StringUtil.isEmpty(subject)){
            sql.append(" AND ")
            .append("ssd.subject_id=:subject ");
        }
        if(!StringUtil.isEmpty(duration)) {
            sql.append("AND ")
            .append("si.upd_date >= :duration ");
            
            dur.append(DateUtil.getSomeDay(duration, DateUtil.sdf8, -7));
            dur.append(DateUtil.STARTTIME_HHmmssSSS);
        }
        Query query = session.createSQLQuery(sql.toString());
        
        if(!StringUtil.isEmpty(duration)) {
            query.setParameter("duration", dur.toString());
        }
        if(!StringUtil.isEmpty(subject)){
            query.setParameter("subject", subject);
        }
        
        return ((Number) query.uniqueResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StoryInfo> getLstByTag(String tagId, String sortKey, String durationKey, int start, int length) {
        Session session = getCurrentSession();
        StringBuffer dur = new StringBuffer();
        StringBuffer sql = new StringBuffer();
        
        sql.append("SELECT ")
        .append("{si.*} ")
        .append("FROM ")
        .append("story_info si, ")
        .append("story_tag_div std ")
        .append("WHERE ")
        .append("si.book_id = std.book_id ");
        if(!StringUtil.isEmpty(tagId)){
            sql.append(" AND ");
            if(tagId.length() > 2){
                sql.append("std.tag_id=:tagId ");
            } else {
                sql.append("LEFT(tag_id,3)=:tagId ");
            }
        }
        if(!StringUtil.isEmpty(durationKey)) {
            sql.append(" AND ")
            .append("si.upd_date >= :duration ");
            
            dur.append(DateUtil.getSomeDay(durationKey, DateUtil.sdf8, -7));
            dur.append(DateUtil.STARTTIME_HHmmssSSS);
        }
        
        if(!StringUtil.isEmpty(sortKey)) {
            sql.append("ORDER BY ");
            if(HqlConst.CR_DATE.equals(sortKey)){
                sql.append("si." + sortKey)
                .append(" DESC");
            } else {
                sql.append(sortKey);
            }
        }
        Query query = session.createSQLQuery(sql.toString()).addEntity("si", StoryInfo.class);
        
        if(!StringUtil.isEmpty(durationKey)) {
            query.setParameter("duration", dur.toString());
        }
        if(!StringUtil.isEmpty(tagId)){
            query.setParameter("tagId", tagId);
        }
        
        if(start > -1) {
            query.setFirstResult(start);
        }
        if(length > -1) {
            query.setMaxResults(length);
        }
        
        return query.list();
    }

    @Override
    public int getCntByTag(String tagId, String sortKey, String durationKey) {
        Session session = getCurrentSession();
        StringBuffer dur = new StringBuffer();
        StringBuffer sql = new StringBuffer();
        
        sql.append("SELECT ")
        .append("count(*) ")
        .append("FROM ")
        .append("story_info si, ")
        .append("story_tag_div std ")
        .append("WHERE ")
        .append("si.book_id = std.book_id ");
        if(!StringUtil.isEmpty(tagId)){
            sql.append(" AND ");
            if(tagId.length() > 2){
                sql.append("std.tag_id=:tagId ");
            } else {
                sql.append("LEFT(tag_id,3)=:tagId ");
            }
        }
        if(!StringUtil.isEmpty(durationKey)) {
            sql.append(" AND ")
            .append("si.upd_date >= :duration ");
            
            dur.append(DateUtil.getSomeDay(durationKey, DateUtil.sdf8, -7));
            dur.append(DateUtil.STARTTIME_HHmmssSSS);
        }
        Query query = session.createSQLQuery(sql.toString());
        
        if(!StringUtil.isEmpty(durationKey)) {
            query.setParameter("duration", dur.toString());
        }
        if(!StringUtil.isEmpty(tagId)){
            query.setParameter("tagId", tagId);
        }
        
        return ((Number) query.uniqueResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StoryInfo> getLstByInputTxt(String inputTxt, int start, int length) {
        Session session = getCurrentSession();
        StringBuffer hql = new StringBuffer();
        
        hql.append("FROM StoryInfo si ")
        .append("where ")
        .append("si.bookTitle LIKE :inputTxt ")
        .append("or ")
        .append("si.bookAuthor LIKE :inputTxt ")
        .append("ORDER BY si.crDate DESC ");
        Query query = session.createQuery(hql.toString());
        
        query.setParameter("inputTxt", "%" + inputTxt + "%");
        
        if(start > -1) {
            query.setFirstResult(start);
        }
        if(length > -1) {
            query.setMaxResults(length);
        }
        
        return query.list();
    }

    @Override
    public int getCntByInputTxt(String inputTxt) {
        
        Session session = getCurrentSession();
        StringBuffer hql = new StringBuffer();
        
        hql.append("Select count(*) FROM StoryInfo si ")
        .append("where ")
        .append("si.bookTitle LIKE :inputTxt ")
        .append("or ")
        .append("si.bookAuthor LIKE :inputTxt ")
        .append("ORDER BY si.crDate DESC ");
        Query query = session.createQuery(hql.toString());
        
        query.setParameter("inputTxt", "%" + inputTxt + "%");
        
        return ((Number)query.uniqueResult()).intValue();
    }

    @Override
    public List<StoryInfo> getlstByNMID(String bookNM, String bookID, int start, int length) {
        Session session = getCurrentSession();
        StringBuffer hql = new StringBuffer();
        hql.append("From StoryInfo si")
        .append(" where 1=1 ");
        if(!StringUtil.isEmpty(bookNM)) {
            hql.append(" and si.bookTitle like :bookNM ");
        }
        
        if(!StringUtil.isEmpty(bookID)) {
            hql.append(" and si.bookId like :bookID ");
        }
        
        hql.append(" order by crDate DESC ");
        
        Query query = session.createQuery(hql.toString());
        
        if(!StringUtil.isEmpty(bookNM)) {
            query.setParameter("bookNM", "%" + bookNM + "%");
        }
        
        if(!StringUtil.isEmpty(bookID)) {
            query.setParameter("bookID", "%" + bookID + "%");
        }
        
        if(start > -1) {
            query.setFirstResult(start);
        }
        if(length > -1) {
            query.setMaxResults(length);
        }
        
        return query.list();
    }

    @Override
    public int getCntByNMID(String bookNM, String bookID) {
        
        Session session = getCurrentSession();
        StringBuffer hql = new StringBuffer();
        hql.append("Select count(*) From StoryInfo si")
        .append(" where 1=1 ");
        if(!StringUtil.isEmpty(bookNM)) {
            hql.append(" and si.bookTitle like :bookNM ");
        }
        
        if(!StringUtil.isEmpty(bookID)) {
            hql.append(" and si.bookId like :bookID ");
        }
        
        Query query = session.createQuery(hql.toString());
        
        if(!StringUtil.isEmpty(bookNM)) {
            query.setParameter("bookNM", "%" + bookNM + "%");
        }
        
        if(!StringUtil.isEmpty(bookID)) {
            query.setParameter("bookID", "%" + bookID + "%");
        }
        
        return ((Number)query.uniqueResult()).intValue();
    }

    @Override
    public List<StoryInfo> getList4Main(String inputTxt, String tag, String status, int ltWords, int gtWords, int start,
            int length) {
        Session session = getCurrentSession();
        
        StringBuffer sql = new StringBuffer();
        
        sql.append("select {si.*} from ")
        .append("story_info si ");
        if(!StringUtil.isEmpty(tag)) {
            sql.append(",story_tag_div std ");
        }
        
        sql.append("where 1=1 ");
        if(!StringUtil.isEmpty(tag)) {
            
            if(tag.length() > 3){
                tag = tag.substring(0, 3);
            }
            
            sql.append("AND ")
            .append("si.book_id = std.book_id ")
            .append("AND ")
            .append("SUBSTR(std.tag_id,1,3)=:tag ");
        }
        
        if(!StringUtil.isEmpty(status)) {
            sql.append("AND ")
            .append("si.book_status=:status ");
        }
        
        if(!StringUtil.isEmpty(inputTxt)) {
            sql.append("AND ")
            .append("(si.book_title LIKE :inputTxt or si.book_author LIKE :inputTxt ) ");
        }
        
        if(ltWords > 0) {
            sql.append("AND ")
            .append("si.word_count <= :ltWords ");
        }
        
        if(gtWords > 0) {
            sql.append("AND ")
            .append("si.word_count >= :gtWords ");
        }
        
        sql.append(" order by si.cr_date DESC ");
        
        Query query = session.createSQLQuery(sql.toString()).addEntity("si", StoryInfo.class);
        
        if(!StringUtil.isEmpty(tag)) {
            query.setParameter("tag", tag);
        }
        
        if(!StringUtil.isEmpty(status)) {
            query.setParameter("status", status);
        }
        
        if(!StringUtil.isEmpty(inputTxt)) {
            query.setParameter("inputTxt", "%" + inputTxt + "%");
        }
        
        if(ltWords > 0) {
            query.setParameter("ltWords", ltWords);
        }
        
        if(gtWords > 0) {
            query.setParameter("gtWords", gtWords);
        }
        
        if(start > -1) {
            query.setFirstResult(start);
        }
        if(length > -1) {
            query.setMaxResults(length);
        }
        
        return query.list();
    }

    @Override
    public int getCnt4Main(String inputTxt, String tag, String status, int ltWords, int gtWords) {
        Session session = getCurrentSession();
        
        StringBuffer sql = new StringBuffer();
        
        sql.append("select count(*) from ")
        .append("story_info si ");
        if(!StringUtil.isEmpty(tag)) {
            sql.append(",story_tag_div std ");
        }
        sql.append("where 1=1 ");
        if(!StringUtil.isEmpty(tag)) {
            
            if(tag.length() > 3){
                tag = tag.substring(0, 3);
            }
            
            sql.append("AND ")
            .append("si.book_id = std.book_id ")
            .append("AND ")
            .append("SUBSTR(std.tag_id,1,3)=:tag ");
        }
        
        if(!StringUtil.isEmpty(status)) {
            sql.append("AND ")
            .append("si.book_status=:status ");
        }
        
        if(!StringUtil.isEmpty(inputTxt)) {
            sql.append("AND ")
            .append("(si.book_title LIKE :inputTxt or si.book_author LIKE :inputTxt ) ");
        }
        
        if(ltWords > 0) {
            sql.append("AND ")
            .append("si.word_count <=:ltWords ");
        }
        
        if(gtWords > 0) {
            sql.append("AND ")
            .append("si.word_count >= :gtWords ");
        }
        
        Query query = session.createSQLQuery(sql.toString());
        
        if(!StringUtil.isEmpty(tag)) {
            query.setParameter("tag", tag);
        }
        
        if(!StringUtil.isEmpty(status)) {
            query.setParameter("status", status);
        }
        
        if(!StringUtil.isEmpty(inputTxt)) {
            query.setParameter("inputTxt", "%" + inputTxt + "%");
        }
        
        if(ltWords > 0) {
            query.setParameter("ltWords", ltWords);
        }
        
        if(gtWords > 0) {
            query.setParameter("gtWords", gtWords);
        }
        
        return ((Number)query.uniqueResult()).intValue();
    }
}
