package com.comm.dao;

import java.util.List;

import com.comm.model.StorySubject;
import com.comm.model.StorySubjectDiv;

public interface StorySubjectDivDao extends ICommonDao<StorySubjectDiv> {

    List<StorySubject> getLstByBookId(String bookId);

}
