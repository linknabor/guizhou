package com.yumu.hexie.service.charger;

import javax.xml.bind.ValidationException;

import com.yumu.hexie.integration.charger.vo.PayStatusResult;
import com.yumu.hexie.integration.wuye.vo.WechatPayInfo;

/**
 * 云充
 * @author Administrator
 *
 */
public interface ChargerService {

	public WechatPayInfo getChargerPayInfo(String user_id, String phone,String openId,String money) throws ValidationException;
	
	public PayStatusResult noticeChargerPay(String openId, String phone, String userId, String tradeWaterId, String packageId);
	
	//把用户注册的信息汇总到servplat的汇总表中，便于以后统计汇总
	public boolean saveChargerUser(String openId, String phone, String sn, String sectId);
}
