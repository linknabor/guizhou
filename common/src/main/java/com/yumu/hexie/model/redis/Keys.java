package com.yumu.hexie.model.redis;

public final class Keys {

    public static String uidCardKey(Long uid) {
        return String.format("uid:%d:cart", uid);
    }
    public static String uidHomeCardKey(Long uid) {
        return String.format("uid:%d:homeCart", uid);
    }
    public static String uidShareAccRecordKey(Long uid) {
        return String.format("uid:%s:shareRecord", uid);
    }
    public static String systemConfigKey(String key) {
        return String.format("systemConfig:%s:", key);
    }
    
    public static String orderCarInfoKey(long uid) {
    	return String.format("uid:%d:orderCarInfo", uid);
    }
}
