package com.comm.dao;

import java.util.List;
import java.util.Map;

import com.comm.model.StorySubject;

public interface StorySubjectDao extends ICommonDao<StorySubject> {

    /**
     * 获取所有的主题
     * @return
     */
    List<Map<String, Object>> getAllSubjects();

}
