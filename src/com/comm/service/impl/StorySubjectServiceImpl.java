package com.comm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.StorySubjectDao;
import com.comm.model.StorySubject;
import com.comm.service.StorySubjectService;
import com.comm.util.DateUtil;
import com.comm.util.HqlConst;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class StorySubjectServiceImpl implements StorySubjectService {
    
    @Autowired
    private StorySubjectDao storySubjectDao;
    
    @Override
    public void save(StorySubject storySubject) {
        storySubjectDao.save(storySubject);
    }

    @Override
    public void delByKey(Integer seq) {
        storySubjectDao.deleteByKeyAndValue(HqlConst.SEQ, seq);
    }

    @Override
    public void update(StorySubject storySubject) {
        storySubjectDao.update(storySubject);
    }

    @Override
    public List<Map<String, Object>> getAllSubjects() {
        return storySubjectDao.getAllSubjects();
    }

    @Override
    public List<StorySubject> getAllSubjectsEnt() {
        return storySubjectDao.listAll();
    }

    @Override
    public void update(JSONObject data) {
        JSONObject obj = JSONObject.fromObject(data);
        JSONArray insert = obj.getJSONArray("insert");
        JSONArray update = obj.getJSONArray("update");
        JSONArray delete = obj.getJSONArray("delete");
        
        String str = DateUtil.getNow17();
        if (null != insert) {
            for (int i = 0; i < insert.size(); i++) {
                
                StorySubject ss = new StorySubject();
                JSONObject jObj = insert.getJSONObject(i);
//                StorySubject ssTmp = storySubjectDao.queryByKeyAndValue(HqlConst.SEQ, jObj.optInt("seq"));
//                if(ssTmp == null) {
                    ss.setSubjectId(jObj.optString("subjectId"));
                    ss.setSubjectName(jObj.optString("subjectName"));
                    ss.setCrDate(str);
                    ss.setUpdDate(str);
                    storySubjectDao.save(ss);
//                }
            }
        }
        if (null != update) {
            for (int i = 0; i < update.size(); i++) {
                
                JSONObject jObj = update.getJSONObject(i);
                StorySubject ssTmp = storySubjectDao.queryByKeyAndValue(HqlConst.SEQ, jObj.optInt("seq"));
                
                if(ssTmp != null) {
                    ssTmp.setSubjectId(jObj.optString("subjectId"));
                    ssTmp.setSubjectName(jObj.optString("subjectName"));
                    ssTmp.setUpdDate(str);
                    storySubjectDao.update(ssTmp);
                }
            }
        }
        if (null != delete) {
            for (int i = 0; i < delete.size(); i++) {
                JSONObject jObj = delete.getJSONObject(i);
                storySubjectDao.deleteByKeyAndValue(HqlConst.SEQ, jObj.optInt("seq"));
            }
        }
    }
}
