package com.comm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.ScrollbarInfoDao;
import com.comm.dao.StoryDirInfoDao;
import com.comm.dao.StoryInfoDao;
import com.comm.dao.StorySubjectDivDao;
import com.comm.dao.StoryTagDivDao;
import com.comm.model.StoryDirInfo;
import com.comm.model.StoryInfo;
import com.comm.model.StorySubjectDiv;
import com.comm.model.StoryTagDiv;
import com.comm.service.StoryInfoService;
import com.comm.util.HqlConst;
import com.comm.util.UUIDUtil;

@Service
@Transactional
public class StoryInfoServiceImpl implements StoryInfoService {
    
    @Autowired
    private StoryInfoDao storyInfoDao;
    
    @Autowired
    private StorySubjectDivDao storySubjectDivDao;
    
    @Autowired
    private StoryDirInfoDao storyDirInfoDao; 
    
    @Autowired
    private StoryTagDivDao storyTagDivDao;
    
    @Autowired
    private ScrollbarInfoDao scrollbarInfoDao;
    
    @Override
    public void save(StoryInfo storyInfo) {
        storyInfoDao.save(storyInfo);
    }

    @Override
    public void delByKey(String bookId) {
        storyInfoDao.deleteByKeyAndValue(HqlConst.BOOK_ID, bookId);
    }

    @Override
    public void update(StoryInfo storyInfo) {
        storyInfoDao.update(storyInfo);
    }

    @Override
    public List<StoryInfo> getLstBySubject(String subject, String sortKey, String duration, int start, int length) {
        return storyInfoDao.getLstBySubject(subject, sortKey, duration, start, length);
    }

    @Override
    public int getCntBySubject(String subject, String sortKey, String duration) {
        return storyInfoDao.getCntBySubject(subject, sortKey, duration);
    }

    @Override
    public List<StoryInfo> getLstByTag(String tagId, String sortKey, String durationKey, int start, int length) {
        return storyInfoDao.getLstByTag(tagId, sortKey, durationKey, start, length);
    }

    @Override
    public int getCntByTag(String tagId, String sortKey, String durationKey) {
        return storyInfoDao.getCntByTag(tagId, sortKey, durationKey);
    }

    @Override
    public StoryInfo getByBookId(String bookId) {
        return storyInfoDao.queryByKeyAndValue(HqlConst.BOOK_ID, bookId);
    }

    @Override
    public List<StoryInfo> getLstByInputTxt(String inputTxt, int start, int length) {
        return storyInfoDao.getLstByInputTxt(inputTxt, start, length);
    }

    @Override
    public int getCntByInputTxt(String inputTxt) {
        return storyInfoDao.getCntByInputTxt(inputTxt);
    }

    @Override
    public void save(StoryInfo si, List<StoryTagDiv> stds, StorySubjectDiv ssd) {
        storyInfoDao.save(si);
        
        if(stds != null) {
            for(StoryTagDiv std:stds) {
                storyTagDivDao.save(std);
            }
        }
        
        storySubjectDivDao.save(ssd);
    }

    @Override
    public List<StoryInfo> getlstByNMID(String bookNM, String bookID, int start, int length) {
        return storyInfoDao.getlstByNMID(bookNM, bookID, start, length);
    }

    @Override
    public int getCntByNMID(String bookNM, String bookID) {
        return storyInfoDao.getCntByNMID(bookNM, bookID);
    }

    @Override
    public void delBookInfo(String bookId) {
        storyInfoDao.deleteByKeyAndValue(HqlConst.BOOK_ID, bookId);
        storyDirInfoDao.deleteByKeyAndValue(HqlConst.BOOK_ID, bookId);
        storySubjectDivDao.deleteByKeyAndValue(HqlConst.BOOK_ID, bookId);
        storyTagDivDao.deleteByKeyAndValue(HqlConst.BOOK_ID, bookId);
        scrollbarInfoDao.deleteByKeyAndValue(new String[]{HqlConst.ITEM_ID, HqlConst.ITEM_TYPE}, new Object[]{bookId, "1"});
        
    }

    @Override
    public void delBookDir(StoryInfo si) {  
        storyInfoDao.update(si);
        storyDirInfoDao.deleteByKeyAndValue(HqlConst.BOOK_ID, si.getBookId());
    }

    @Override
    public void saveUpdBook(StoryInfo si, List<StoryDirInfo> dirs) {
        storyInfoDao.update(si);
        if(dirs.size() > 0) {
            for(int i=0; i < dirs.size(); i++) {
                StoryDirInfo sd = dirs.get(i);
                sd.setUuid(UUIDUtil.minimizedUUID());
                storyDirInfoDao.save(sd);
            }
        }
    }

    @Override
    public List<StoryInfo> getList4Main(String inputTxt, String tag, String status, int ltWords, int gtWords, int start,
            int length) {
        return storyInfoDao.getList4Main(inputTxt, tag, status, ltWords, gtWords, start,
                length);
    }

    @Override
    public int getCnt4Main(String inputTxt, String tag, String status, int ltWords, int gtWords) {
        return storyInfoDao.getCnt4Main(inputTxt, tag, status, ltWords, gtWords);
    }

}
