package com.comm.service;

import java.util.List;
import java.util.Map;

import com.comm.model.StoryDirInfo;

public interface StoryDirInfoService {
    void save(StoryDirInfo storyDirInfo);
    void delByKey(String uuid);
    void update(StoryDirInfo storyDirInfo);
    /**
     * 根据 bookId 获取相对应的目录 
     * @param bookId
     * @return
     */
    List<Map<String, Object>> getLstByBookId(String bookId);
    List<StoryDirInfo> getFullLstByBookId(String bookId);
}
