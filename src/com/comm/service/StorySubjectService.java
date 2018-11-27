package com.comm.service;

import java.util.List;
import java.util.Map;

import com.comm.model.StorySubject;

import net.sf.json.JSONObject;

public interface StorySubjectService {
    void save(StorySubject storySubject);
    void delByKey(Integer seq);
    void update(StorySubject storySubject);
    /**
     * 获取所有的主题
     * @return
     */
    List<Map<String, Object>> getAllSubjects();
    /**
     * 获取所有的主题
     * @return
     */
    List<StorySubject> getAllSubjectsEnt();
    /**
     * 更新列表，增删改
     * @param data
     */
    void update(JSONObject data);
}
