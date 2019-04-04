package com.yumu.hexie.integration.amap;

import com.yumu.hexie.common.util.ConfigUtil;

/**
 * 高德地图配置
 */
public class ConstantAmap {
	public static String DEFAULTLIMIT = ConfigUtil.get("amapList");
	public static String DEFAULTPAGE = ConfigUtil.get("amapPage");
	public static String AMAPTABLEID = ConfigUtil.get("amapTableid");
	public static String AMAPCLOUDKEY = ConfigUtil.get("amapCloudKey");
}
