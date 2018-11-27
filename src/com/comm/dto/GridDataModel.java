package com.comm.dto;

import java.util.ArrayList;
import java.util.List;

public class GridDataModel<T>
{
  private int total;
  private List<T> rows = new ArrayList<T>();
  private List<Column> colmodel = new ArrayList<Column>();

  private String dataStr = "";
  
  private String provinceNum = "";
  
  private String cityNum = "";
  
  private String startT = "";
  
  private String endT ="";
  
  private String cycle = "";
  
  private String res = "";
  
  private String channelCode = "";
  
  private String telNum="";
  
  private String orderNo="";
  
  private String actFlg="";
  
  public String getTelNum() {
	return telNum;
}

public void setTelNum(String telNum) {
	this.telNum = telNum;
}

public String getOrderNo() {
	return orderNo;
}

public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

private String status="";
  
public String getCycle() {
	return cycle;
}

public void setCycle(String cycle) {
	this.cycle = cycle;
}

public String getStartT() {
	return startT;
}

public void setStartT(String startT) {
	this.startT = startT;
}

public String getEndT() {
	return endT;
}

public void setEndT(String endT) {
	this.endT = endT;
}

public String getProvinceNum() {
	return provinceNum;
}

public void setProvinceNum(String provinceNum) {
	this.provinceNum = provinceNum;
}

public String getCityNum() {
	return cityNum;
}

public void setCityNum(String cityNum) {
	this.cityNum = cityNum;
}

public String getDataStr() {
	return dataStr;
}

public void setDataStr(String dataStr) {
	this.dataStr = dataStr;
}

public List<Column> getColmodel()
  {
    return this.colmodel;
  }

  public void setColmodel(List<Column> paramList)
  {
    this.colmodel = paramList;
  }

  public List<T> getRows()
  {
    return this.rows;
  }

  public void setRows(List<T> paramList)
  {
    this.rows = paramList;
  }

  public int getTotal()
  {
    return this.total;
  }

  public void setTotal(int paramInt)
  {
    this.total = paramInt;
  }

public String getRes() {
	return res;
}

public void setRes(String res) {
	this.res = res;
}

public String getChannelCode() {
	return channelCode;
}

public void setChannelCode(String channelCode) {
	this.channelCode = channelCode;
}

public String getActFlg() {
	return actFlg;
}

public void setActFlg(String actFlg) {
	this.actFlg = actFlg;
}


}
