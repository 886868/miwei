package com.comm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comm.dao.AdInfoDao;
import com.comm.dao.ScrollbarInfoDao;
import com.comm.dao.StoryInfoDao;
import com.comm.model.AdInfo;
import com.comm.model.ScrollbarInfo;
import com.comm.model.StoryInfo;
import com.comm.service.ScrollbarInfoService;
import com.comm.util.Const;
import com.comm.util.DateUtil;
import com.comm.util.HqlConst;
import com.comm.util.UUIDUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class ScrollbarInfoServiceImpl implements ScrollbarInfoService {
    @Autowired
    private ScrollbarInfoDao scrollbarInfoDao;
    
    @Autowired
    private StoryInfoDao storyInfoDao;
    
    @Autowired
    private AdInfoDao adInfoDao;

    @Override
    public JSONArray getLstByBz(String divBz) {
        List<ScrollbarInfo>lst = scrollbarInfoDao.listByKeyAndValue(HqlConst.DIV_BZ, divBz);
        JSONArray result = new JSONArray();
        if(lst != null && lst.size() > 0) {
            for(ScrollbarInfo si:lst) {
                // 判断banner的类型
                if(Const.SI_ITEAM_TYPE_STORY.equals(si.getItemType())) {
                    
                    StoryInfo obj = storyInfoDao.queryByKeyAndValue(HqlConst.BOOK_ID, si.getItemId());
                    
                    if(obj != null) {
                        // 塞入图书的 id，bannerimgurl，title,iteam类型
                        JSONObject job = new JSONObject();
                        job.put("iteamId", obj.getBookId());
                        job.put("cover", obj.getCoverBannerUrl());
                        job.put("title", obj.getBookTitle());
                        job.put("url", "");
                        job.put("iteamType", Const.SI_ITEAM_TYPE_STORY);
                        result.add(job);
                    }
                    
                } else if(Const.SI_ITEAM_TYPE_AD.equals(si.getItemType())) {
                    // 获取广告的内容
                    AdInfo ai = adInfoDao.queryByKeyAndValue(HqlConst.AD_ID, si.getItemId());
                    if(ai != null) {
                        JSONObject job = new JSONObject();
                        job.put("iteamId", ai.getAdId());
                        job.put("cover", ai.getAdScbarUrl());
                        job.put("title", ai.getAdTitle());
                        job.put("url", ai.getAdUrl());
                        job.put("iteamType", Const.SI_ITEAM_TYPE_AD);
                        result.add(job);
                    }
                }
            }
        }
        
        return result;
    }

    @Override
    public List<ScrollbarInfo> getAll() {
        return scrollbarInfoDao.listAll();
    }

    @Override
    public void update(JSONObject data) {
        JSONObject obj = JSONObject.fromObject(data);
        JSONArray insert = obj.getJSONArray("insert");
        JSONArray update = obj.getJSONArray("update");
        JSONArray delete = obj.getJSONArray("delete");
        
        String str = DateUtil.getNow17();
        if (null != insert) {
            for (int i = 0; i < insert.size(); i++) {
                
                ScrollbarInfo st = new ScrollbarInfo();
                JSONObject jObj = insert.getJSONObject(i);
                String uuid = UUIDUtil.minimizedUUID();
                st.setUuid(uuid);
                st.setItemId(jObj.optString("itemId"));
                st.setItemType(jObj.optString("itemType"));
                st.setDivBz(jObj.optString("divBz"));
                st.setUpdDate(str);
                st.setCrDate(str);
                scrollbarInfoDao.save(st);
            }
        }
        if (null != update) {
            for (int i = 0; i < update.size(); i++) {
                JSONObject jObj = update.getJSONObject(i);
                ScrollbarInfo st = scrollbarInfoDao.queryByKeyAndValue(HqlConst.UUID, jObj.optString("uuid"));
                
                if(st != null) {
                    st.setItemId(jObj.optString("itemId"));
                    st.setItemType(jObj.optString("itemType"));
                    st.setDivBz(jObj.optString("divBz"));
                    st.setUpdDate(str);
                    scrollbarInfoDao.update(st);
                }
            }
        }
        if (null != delete) {
            for (int i = 0; i < delete.size(); i++) {
                JSONObject jObj = delete.getJSONObject(i);
                scrollbarInfoDao.deleteByKeyAndValue(HqlConst.UUID, jObj.optString("uuid"));
            }
        }
    }
}
