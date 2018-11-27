package com.comm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.StorySubjectDivDao;
import com.comm.model.StorySubject;
import com.comm.model.StorySubjectDiv;
import com.comm.service.StorySubjectDivService;
import com.comm.util.HqlConst;

@Service
@Transactional
public class StorySubjectDivServiceImpl implements StorySubjectDivService {

    @Autowired
    private StorySubjectDivDao storySubjectDivDao;
    
    @Override
    public void save(StorySubjectDiv storySubjectDiv) {
        storySubjectDivDao.save(storySubjectDiv);
    }

    @Override
    public void delByKey(String uuid) {
        storySubjectDivDao.deleteByKeyAndValue(HqlConst.UUID, uuid);
    }

    @Override
    public void update(StorySubjectDiv storySubjectDiv) {
        storySubjectDivDao.update(storySubjectDiv);
    }

    @Override
    public List<StorySubject> getLstByBookId(String bookId) {
        return storySubjectDivDao.getLstByBookId(bookId);
    }

    @Override
    public void del(String bookId, String subId) {
        storySubjectDivDao.deleteByKeyAndValue(new String[]{HqlConst.BOOK_ID, HqlConst.SUBJECT_ID}, new Object[]{bookId, subId});
    }

    @Override
    public StorySubjectDiv getIteam(String bookId, String subId) {
        return storySubjectDivDao.queryByKeysAndValues(new String[]{HqlConst.BOOK_ID, HqlConst.SUBJECT_ID}, new Object[]{bookId, subId});
    }
}
