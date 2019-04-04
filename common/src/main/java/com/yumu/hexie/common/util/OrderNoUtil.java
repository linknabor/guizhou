package com.yumu.hexie.common.util;

import java.util.Date;

public class OrderNoUtil {

	public static String generateServiceOrderNo() {
		return DateUtil.dtFormat(new Date(), "yyyyMMddHHmm") +"S" + (int)(1000+(Math.random()*9000)) + System.currentTimeMillis() % 10;
	}

	public static String generateGroupNo() {
		return DateUtil.dtFormat(new Date(), "yyyyMMddHHmm") +"G" + (int)(1000+(Math.random()*9000)) + System.currentTimeMillis() % 10;
	}
	public static String generateYuyueOrderNo() {
		return DateUtil.dtFormat(new Date(), "yyyyMMddHHmm") +"Y" + (int)(1000+(Math.random()*9000)) + System.currentTimeMillis() % 10;
	}


    public static String generateO2OOrderNo() {
        return DateUtil.dtFormat(new Date(), "yyyyMMddHHmm") +"O" + (int)(1000+(Math.random()*9000)) + System.currentTimeMillis() % 10;
    }
	public static String generatePaymentOrderNo() {
		return DateUtil.dtFormat(new Date(), "yyyyMMddHHmm") +"P" + (int)(1000+(Math.random()*9000)) + System.currentTimeMillis() % 10;
	}

	public static String generateRefundOrderNo() {
		return DateUtil.dtFormat(new Date(), "yyyyMMddHHmm") +"R" + (int)(1000+(Math.random()*9000)) + System.currentTimeMillis() % 10;
	}
	

    public static String generateRepairOrderNo() {
        return DateUtil.dtFormat(new Date(), "yyyyMMddHHmm") +"W" + (int)(1000+(Math.random()*9000)) + System.currentTimeMillis() % 10;
    }
    

    public static String generateSettleOrderNo() {
        return DateUtil.dtFormat(new Date(), "yyyyMMddHHmm") +"T" + (int)(1000+(Math.random()*9000)) + System.currentTimeMillis() % 10;
    }
}
