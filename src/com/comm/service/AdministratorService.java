package com.comm.service;

import java.util.Map;

import com.comm.model.Administrator;

public interface AdministratorService {

	Map<String, Object> getDetailsByAdminId(String adminId,String name,String num,String city,String rule,int start,int limit);
	void addAdmin(Administrator admin);
	void updateAdmin(Administrator admin);
	void delAdminBySeq(String adminId);
	String administratordo(String json);
	boolean login(Administrator admin);
	Administrator getAdmin(String adminId);
}
