package com.yumu.hexie.service.user;

import com.yumu.hexie.model.user.User;

public interface PointService {

	public void addLvdou(User user,int point, String key);
	
	public void addZhima(User user,int point, String key);
}
