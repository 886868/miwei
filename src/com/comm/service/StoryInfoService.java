package com.comm.service;

import java.util.List;

import com.comm.model.StoryDirInfo;
import com.comm.model.StoryInfo;
import com.comm.model.StorySubjectDiv;
import com.comm.model.StoryTagDiv;

public interface StoryInfoService {
    void save(StoryInfo storyInfo);
    void delByKey(String bookId);
    void update(StoryInfo storyInfo);
    /**
     * 根据主题id 分页获取小说列表
     * @param subject
     * @param sortKey
     * @param duration
     * @return List<StoryInfo>
     */
    List<StoryInfo> getLstBySubject(String subject, String sortKey, String duration, int start, int length);
    
    /**
     * 根据主题id 获取记录总数
     * @param subject
     * @param sortKey
     * @param duration
     * @return
     */
    int getCntBySubject(String subject, String sortKey, String duration);
    /**
     * 根据tag 获取记录总数
     * @param tagId
     * @param sortKey
     * @param durationKey
     * @param start
     * @param length
     * @return
     */
    List<StoryInfo> getLstByTag(String tagId, String sortKey, String durationKey, int start, int length);
    /**
     * 根据tagId 获取记录总数
     * @param tagId
     * @param sortKey
     * @param duration
     * @return
     */
    int getCntByTag(String tagId, String sortKey, String durationKey);
    /**
     * 根据 bookId 获取数据
     * @param bookId
     * @return
     */
    StoryInfo getByBookId(String bookId);
    /**
     * 根据 inputTxt 按照书名或作者名进行分页查询
     * @param inputTxt
     * @param start
     * @param length
     * @return
     */
    List<StoryInfo> getLstByInputTxt(String inputTxt, int start, int length);
    /**
     * 根据 inputTxt 按照书名或作者名查询总数
     * @param inputTxt
     * @param start
     * @param length
     * @return
     */
    int getCntByInputTxt(String inputTxt);
    /**
     * 后台新建小说
     * @param si
     * @param std
     */
    void save(StoryInfo si, List<StoryTagDiv> stds, StorySubjectDiv ssd);
    /**
     * 根据小说id或者名称模糊查询
     * @param bookNM
     * @param bookID
     * @return
     */
    List<StoryInfo> getlstByNMID(String bookNM, String bookID, int start, int length);
    /**
     * 根据小说id或者名称模糊查询总数
     * @param bookNM
     * @param bookID
     * @return
     */
    int getCntByNMID(String bookNM, String bookID);
    /**
     * 删除小说及其相关的表
     * @param bookId
     */
    void delBookInfo(String bookId);
    /**
     * 删除小说目录
     * @param si
     */
    void delBookDir(StoryInfo si);
    /**
     * 上传小说，保存目录数据，更新小说目录信息
     * @param si
     * @param dirs
     */
    void saveUpdBook(StoryInfo si, List<StoryDirInfo> dirs);
    List<StoryInfo> getList4Main(String inputTxt, String tag, String status, int ltWords, int gtWords, int start,
            int length);
    int getCnt4Main(String inputTxt, String tag, String status, int ltWords, int gtWords);
}
