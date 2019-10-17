package com.yumu.hexie.common.util;

import java.util.Map;
import java.util.TreeMap;

public class UnionUtil {
	
	public static boolean verferSignData(String str) {
        String data[] = str.split("&");
        StringBuffer buf = new StringBuffer();
        String signature = "";
        for (int i = 0; i < data.length; i++) {
            String tmp[] = data[i].split("=", 2);
            if ("signature".equals(tmp[0])) {
                signature = tmp[1];
            } else {
                buf.append(tmp[0]).append("=").append(tmp[1]).append("&");
            }
        }
        String signatureStr = buf.substring(0, buf.length() - 1);
        return RSAUtil.verifyByKeyPath(signatureStr, signature, "f:/keys/unionpay/888290059501308_pub.pem", "UTF-8");
    }
	
	public static Map<String, String> pullRespToMap(String str) {
		Map<String, String> map = new TreeMap<String, String>();
		String data[] = str.split("&");
        for (int i = 0; i < data.length; i++) {
            String tmp[] = data[i].split("=", 2);
            map.put(tmp[0], tmp[1]);
        }
        return map;
	}
	public static String mapToStr(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		for (String key : map.keySet()) {
			sb.append(key).append("=").append(map.get(key)).append("&");
		}
		String signatureStr = sb.substring(0, sb.length() - 1);
		return signatureStr;
	}
}
