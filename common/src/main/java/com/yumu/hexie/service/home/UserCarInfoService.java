package com.yumu.hexie.service.home;

import java.util.List;

import com.yumu.hexie.model.extreinfo.CarBrandName;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserCarInfo;


/**
 * 用户车辆信息服务
 */
public interface UserCarInfoService {

	//添加车辆
	public UserCarInfo addUserCarInfo(UserCarInfo userCarInfo);
	//根据id查询车辆
	public UserCarInfo queryUserCarInfoById(long id);
	//删除车辆信息
	public void deleteUserCarInfo(long id,long userId);
	//查询用户下所有的车辆
	public List<UserCarInfo> queryUserCarInfoByUser(long userId);
	//设置默认车辆
	public boolean configDefaultUserCarInfo(User user, long UserCarInfoId);
	//获取汽车品牌
	public List<CarBrandName> getMakeName();
	//根据汽车品牌获取型号
	public List<String> getModelByMakeName(String makeName);
}
