package com.yumu.hexie.service.user.req;

import com.yumu.hexie.common.util.ConfigUtil;

public class MemberVo {
	public static final float PRICE = 98f;
	public static final String MIDDLE = "01";//正在支付中
	public static final String SUCCESS = "02";//支付成功
	public static final String FAIL = "03";//交易失败（唤起没有支付的订单）
	public static final String MEMBER_YES = "0";
	public static final String MEMBER_NO = "1";
	public static final String NOTIFYURL = ConfigUtil.get("memberNotifyurl");
	
}
