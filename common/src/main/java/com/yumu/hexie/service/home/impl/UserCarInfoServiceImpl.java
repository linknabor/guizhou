package com.yumu.hexie.service.home.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.model.extreinfo.CarBrandName;
import com.yumu.hexie.model.extreinfo.CarInfoRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserCarInfo;
import com.yumu.hexie.model.user.UserCarInfoRepository;
import com.yumu.hexie.service.home.UserCarInfoService;

@Service("userCarInfoService")
public class UserCarInfoServiceImpl implements UserCarInfoService {

	private static final Logger log = LoggerFactory.getLogger(UserCarInfoServiceImpl.class);

	@Inject
	private UserCarInfoRepository userCarInfoRepository;
	@Inject
	private CarInfoRepository carInfoRepository;

	@Override
	public UserCarInfo addUserCarInfo(UserCarInfo userCarInfo) {
		try {
			log.error(JacksonJsonUtil.beanToJson(userCarInfo));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return userCarInfoRepository.save(userCarInfo);
	}
	@Override
	public boolean configDefaultUserCarInfo(User user, long userCarInfoId) {
		List<UserCarInfo> userCarInfos = queryUserCarInfoByUser(user.getId());
		UserCarInfo defaultUserCarInfo = null;
		for(UserCarInfo userCarInfo:userCarInfos) {
			if(userCarInfo.getId() == userCarInfoId) {
				userCarInfo.setMain(true);
				defaultUserCarInfo = userCarInfo;
			} else {
				userCarInfo.setMain(false);
			}
		}
		if(defaultUserCarInfo != null) {
			userCarInfoRepository.save(userCarInfos);
		}
		return defaultUserCarInfo != null;
	}
	@Override
	public void deleteUserCarInfo(long id, long userId) {
		userCarInfoRepository.deleteCar(id, userId);
	}
	@Override
	public List<UserCarInfo> queryUserCarInfoByUser(long userId) {
		return userCarInfoRepository.findAllByUserId(userId);
	}
	@Override
	public UserCarInfo queryUserCarInfoById(long id) {
		UserCarInfo userCarInfo = userCarInfoRepository.findOne(id);
		return userCarInfo;
	}

	@Override
	public List<CarBrandName> getMakeName() {
		List<Object[]> lists = new ArrayList<Object[]>();
		List<CarBrandName> carBrandNames= new ArrayList<CarBrandName>();
		List<String> brandNames = new ArrayList<String>();

		lists = carInfoRepository.findAllMakeName();

		for(int i = 0; i < lists.size(); i++){
			Object[] obj = (Object[])lists.get(i);
			brandNames.add(obj[0].toString());
			if(i != lists.size()-1){
				Object[] objtemp = (Object[])lists.get(i+1);
				if(obj[1].toString().equals(objtemp[1].toString())){
//					System.out.println(brandNames);
				}else{
					CarBrandName carBrandName = new CarBrandName();
					carBrandName.setKey(obj[1].toString());
					carBrandName.setBrandNames(new ArrayList<String>(brandNames));
					carBrandNames.add(carBrandName);
//					System.out.println(obj[1].toString() + brandNames);
					brandNames.clear();
				}
			}else{
				CarBrandName carBrandName = new CarBrandName();
				carBrandName.setKey(obj[1].toString());
				carBrandName.setBrandNames(new ArrayList<String>(brandNames));
				carBrandNames.add(carBrandName);
//				System.out.println(obj[1].toString() + brandNames);
				brandNames.clear();
			}
		}	
		return carBrandNames;
	}

	@Override
	public List<String> getModelByMakeName(String makeName) {
		return carInfoRepository.findAllModelNameBymakeName(makeName);
	}

}
