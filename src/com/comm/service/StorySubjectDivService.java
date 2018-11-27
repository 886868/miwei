package com.comm.service;

import java.util.List;

import com.comm.model.StorySubject;
import com.comm.model.StorySubjectDiv;

public interface StorySubjectDivService {
    void save(StorySubjectDiv storySubjectDiv);
    void delByKey(String uuid);
    void update(StorySubjectDiv storySubjectDiv);
    List<StorySubject> getLstByBookId(String bookId);
    void del(String bookId, String subId);
    StorySubjectDiv getIteam(String bookId, String subId);
}
