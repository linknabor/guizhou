package com.yumu.hexie.service.charger.impl;

import javax.xml.bind.ValidationException;

import org.springframework.stereotype.Service;

import com.yumu.hexie.integration.charger.ChargerUtil;
import com.yumu.hexie.integration.charger.vo.PayStatusResult;
import com.yumu.hexie.integration.wuye.vo.WechatPayInfo;
import com.yumu.hexie.service.charger.ChargerService;

@Service("chargerService")
public class ChargerServiceImpl implements ChargerService {

	@Override
	public WechatPayInfo getChargerPayInfo(String user_id, String phone, String openId, String money) throws ValidationException {
		return ChargerUtil.getChargerPay(user_id, phone, openId, money).getData() ;
	}

	@Override
	public PayStatusResult noticeChargerPay(String openId, String phone, String userId, String tradeWaterId, String packageId) {
		return ChargerUtil.noticeChargerPay(openId, phone, userId, tradeWaterId, packageId);
	}

	@Override
	public boolean saveChargerUser(String openId, String phone, String sn, String sectId) {
		return ChargerUtil.saveChargerUser(openId, phone, sn, sectId).isSuccess();
	}
}
