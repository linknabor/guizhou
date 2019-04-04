package com.yumu.hexie.integration.wuye.util;

import java.util.HashMap;
import java.util.Map;

public class KeyToNameUtil {

	private static Map<Integer,Map<String,String>> keyNameConfig = new HashMap<Integer,Map<String,String>>();
	public static Integer FEE_TYPE = 1;
	public static Integer HOUSE_TYPE = 2;
	public static Integer INOUT_TYPE = 3;
	public static Integer PAYSTATUS_TYPE = 4;
	public static Integer PAYMETHOD_TYPE = 5;
	public static Integer PARK_TYPE = 6;
	public static Integer PAYSTATUS2_TYPE = 7;
	static {
		Map<String,String> keyName = new HashMap<String, String>();
		keyName.put("0101","物业管理费");
		keyName.put("0102","电梯、水泵运行费");
		keyName.put("0103","保洁服务费");
		keyName.put("0104","保安服务费");
		keyName.put("0105","固定车位停车费");
		keyName.put("0106","公共车位停车费");
		keyName.put("0200","租金");
		keyNameConfig.put(FEE_TYPE, keyName);

		keyName = new HashMap<String, String>();
		keyName.put("1","商品房");
		keyName.put("2","售后公房");
		keyName.put("3","有限产权");
		keyName.put("4","系统公房");
		keyName.put("5","直管公房");
		keyName.put("6","经适房");
		keyName.put("7","廉租房");
		keyNameConfig.put(HOUSE_TYPE, keyName);
		
		keyName = new HashMap<String, String>();
		keyName.put("01","室内");
		keyName.put("02","室外");
		keyNameConfig.put(INOUT_TYPE, keyName);
		
		keyName = new HashMap<String, String>();
		keyName.put("1","未缴费");
		keyName.put("2","已缴费");
		keyNameConfig.put(PAYSTATUS_TYPE, keyName);
		

		keyName = new HashMap<String, String>();
		keyName.put("01","现金");
		keyName.put("02","转账");
		keyName.put( "03","POS");
		keyName.put("04","支付宝");
		keyName.put("05","财付通");
		keyName.put("06","微信支付");
		keyName.put("07","微信支付");
		keyNameConfig.put(PAYMETHOD_TYPE, keyName);
		
		keyName = new HashMap<String, String>();
		keyName.put("01","固定车位");
		keyName.put("02","公共车位");
		keyName.put("03","私家车位");
		keyName.put("04","租赁车位");
		keyNameConfig.put(PARK_TYPE, keyName);
		
		keyName = new HashMap<String, String>();
		keyName.put("02","未缴");
		keyName.put("01","已缴");
		keyNameConfig.put(PAYSTATUS2_TYPE, keyName);
	}
	public static String keyToName(Integer key,String type){
	    String result = keyNameConfig.get(key).get(type);
	    if(result == null) {
	    	return ""+key;
	    } else {
	    	return result;
	    }
	}
}
