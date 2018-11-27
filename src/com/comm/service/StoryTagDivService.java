package com.comm.service;

import java.util.List;

import com.comm.model.StoryTag;
import com.comm.model.StoryTagDiv;

public interface StoryTagDivService {
    void save(StoryTagDiv storyTagDiv);
    void delByKey(String uuid);
    void update(StoryTagDiv storyTagDiv);
    List<StoryTag> getLstByBookId(String bookId);
    void del(String bookId, String tagId);
    StoryTagDiv getItem(String bookId, String tagId);
}
