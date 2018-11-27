package com.comm.dao;

import java.util.List;
import java.util.Map;

import com.comm.model.StoryTag;

public interface StoryTagDao extends ICommonDao<StoryTag> {
    /**
     * 根据小说id，获取小说对应的tags
     * @param bookId
     * @return
     */
    List<Map<String, Object>> getTagsByBookId(String bookId);

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

}
