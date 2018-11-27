package com.comm.dao;

import java.util.List;

import com.comm.model.StoryTag;
import com.comm.model.StoryTagDiv;

public interface StoryTagDivDao extends ICommonDao<StoryTagDiv> {

    List<StoryTag> getLstByBookId(String bookId);

}
