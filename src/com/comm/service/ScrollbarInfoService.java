package com.comm.service;

import java.util.List;

import com.comm.model.ScrollbarInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface ScrollbarInfoService {

    /**
     * 根据业务获取列表
     * @param divBz
     * @return
     */
    JSONArray getLstByBz(String divBz);

    List<ScrollbarInfo> getAll();

    void update(JSONObject data);

}
