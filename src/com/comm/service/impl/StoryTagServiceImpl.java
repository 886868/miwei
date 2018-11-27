package com.comm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.StoryTagDao;
import com.comm.model.StoryTag;
import com.comm.service.StoryTagService;
import com.comm.util.DateUtil;
import com.comm.util.HqlConst;
import com.comm.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class StoryTagServiceImpl implements StoryTagService {
    
    @Autowired
    private StoryTagDao storyTagDao;
    
    @Override
    public void save(StoryTag storyTag) {
        storyTagDao.save(storyTag);
    }

    @Override
    public void delByKey(String tagId) {
        storyTagDao.deleteByKeyAndValue(HqlConst.TAG_ID, tagId);
    }

    @Override
    public void update(StoryTag storyTag) {
        storyTagDao.update(storyTag);
    }

    @Override
    public List<Map<String, Object>> getTagsByBookId(String bookId) {
        return storyTagDao.getTagsByBookId(bookId);
    }

    @Override
    public List<Map<String, Object>> getFirstTags() {
        return storyTagDao.getFirstTags();
    }

    @Override
    public List<Map<String, Object>> getTagsByFirst(String tagId) {
        return storyTagDao.getTagsByFirst(tagId);
    }

    @Override
    public List<StoryTag> getAll() {
        return storyTagDao.listByKeysAndValues(new String[]{}, null, -1, -1, "tagType, parentTagId");
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
                
                StoryTag st = new StoryTag();
                JSONObject jObj = insert.getJSONObject(i);
                
                StoryTag stTmp = null;
                if(!StringUtil.isEmpty(jObj.optString("tagId"))) {
                    stTmp = storyTagDao.queryByKeyAndValue(HqlConst.TAG_ID, jObj.optString("tagId"));
                }
                
                if(stTmp == null) {
                    st.setParentTagId(jObj.optString("parentTagId"));
                    st.setTagColor(jObj.optString("tagColor"));
                    st.setTagId(jObj.optString("tagId"));
                    st.setTagName(jObj.optString("tagName"));
                    st.setTagType(jObj.optString("tagType"));
                    st.setUpdDate(str);
                    st.setCrDate(str);
                    storyTagDao.save(st);
                }
            }
        }
        if (null != update) {
            for (int i = 0; i < update.size(); i++) {
                
                JSONObject jObj = update.getJSONObject(i);
                StoryTag st = storyTagDao.queryByKeyAndValue(HqlConst.TAG_ID, jObj.optString("tagId"));
                
                if(st != null) {
                    st.setParentTagId(jObj.optString("parentTagId"));
                    st.setTagColor(jObj.optString("tagColor"));
                    st.setTagId(jObj.optString("tagId"));
                    st.setTagName(jObj.optString("tagName"));
                    st.setTagType(jObj.optString("tagType"));
                    st.setUpdDate(str);
                    storyTagDao.update(st);
                }
            }
        }
        if (null != delete) {
            for (int i = 0; i < delete.size(); i++) {
                JSONObject jObj = delete.getJSONObject(i);
                storyTagDao.deleteByKeyAndValue(HqlConst.TAG_ID, jObj.optString("tagId"));
            }
        }
    }
}
