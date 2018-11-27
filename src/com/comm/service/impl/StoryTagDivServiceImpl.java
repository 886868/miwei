package com.comm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.StoryTagDivDao;
import com.comm.model.StoryTag;
import com.comm.model.StoryTagDiv;
import com.comm.service.StoryTagDivService;
import com.comm.util.HqlConst;

@Service
@Transactional
public class StoryTagDivServiceImpl implements StoryTagDivService {
    
    @Autowired
    private StoryTagDivDao storyTagDivDao;
    
    @Override
    public void save(StoryTagDiv storyTagDiv) {
        storyTagDivDao.save(storyTagDiv);
    }

    @Override
    public void delByKey(String uuid) {
        storyTagDivDao.deleteByKeyAndValue(HqlConst.UUID, uuid);
    }

    @Override
    public void update(StoryTagDiv storyTagDiv) {
        storyTagDivDao.update(storyTagDiv);
    }

    @Override
    public List<StoryTag> getLstByBookId(String bookId) {
        return storyTagDivDao.getLstByBookId(bookId);
    }

    @Override
    public void del(String bookId, String tagId) {
        storyTagDivDao.deleteByKeyAndValue(new String[]{HqlConst.BOOK_ID, HqlConst.TAG_ID}, new Object[]{bookId, tagId});
    }

    @Override
    public StoryTagDiv getItem(String bookId, String tagId) {
        return storyTagDivDao.queryByKeysAndValues(new String[]{HqlConst.BOOK_ID, HqlConst.TAG_ID}, new Object[]{bookId,tagId});
    }

}
