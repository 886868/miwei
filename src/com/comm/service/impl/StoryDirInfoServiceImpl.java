package com.comm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.StoryDirInfoDao;
import com.comm.model.StoryDirInfo;
import com.comm.service.StoryDirInfoService;
import com.comm.util.HqlConst;

@Service
@Transactional
public class StoryDirInfoServiceImpl implements StoryDirInfoService {
    
    @Autowired
    private StoryDirInfoDao storyDirInfoDao;

    @Override
    public void save(StoryDirInfo storyDirInfo) {
        storyDirInfoDao.save(storyDirInfo);
    }

    @Override
    public void delByKey(String uuid) {
        storyDirInfoDao.deleteByKeyAndValue(HqlConst.UUID, uuid);
    }

    @Override
    public void update(StoryDirInfo storyDirInfo) {
        storyDirInfoDao.update(storyDirInfo);
    }

    @Override
    public List<Map<String, Object>> getLstByBookId(String bookId) {
        return storyDirInfoDao.getLstByBookId(bookId);
    }

    @Override
    public List<StoryDirInfo> getFullLstByBookId(String bookId) {
        return storyDirInfoDao.listByKeysAndValues(new String[]{HqlConst.BOOK_ID}, new String[]{bookId}, -1, -1, "chNo");
    }

}
