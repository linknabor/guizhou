package com.yumu.hexie.service.shequ;

import com.yumu.hexie.integration.wuye.vo.CarFeeInfo;
import com.yumu.hexie.integration.wuye.vo.WechatPayInfo;

public interface CarFeeService {

	//1.根据设备号，查询车辆停车记录
	public CarFeeInfo getCarFeeByDriveNo(String device_no);
	
	//2.根据停车记录微信支付
	public WechatPayInfo getCarPayByWaterId(String userId,String water_id,String openid);
	
}
