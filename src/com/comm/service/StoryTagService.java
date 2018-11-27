package com.comm.service;

import java.util.List;
import java.util.Map;

import com.comm.model.StoryTag;

import net.sf.json.JSONObject;

public interface StoryTagService {
    void save(StoryTag storyTag);
    void delByKey(String tagId);
    void update(StoryTag storyTag);
    /**
     * 根据小说id，获取小说对应的tags
     * @param bookId
     * @return
     */
    List<Map<String,Object>> getTagsByBookId(String bookId);
    /**
     * 获取所有一级tags
     * @return
     */
    List<Map<String, Object>> getFirstTags();
    /**
     * 根据一级 tagsid 获取 tags
     * @param tagsId
     * @return
     */
    List<Map<String, Object>> getTagsByFirst(String tagId);
    /**
     * 获取所有数据
     * @return
     */
    List<StoryTag> getAll();
    void update(JSONObject data);
}
