package com.comm.dao;

import java.util.List;
import java.util.Map;

import com.comm.model.StoryDirInfo;

public interface StoryDirInfoDao extends ICommonDao<StoryDirInfo>{

    /**
     * 根据 bookId 获取相对应的目录 
     * @param bookId
     * @return
     */
    List<Map<String, Object>> getLstByBookId(String bookId);

}
