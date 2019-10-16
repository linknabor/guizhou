package com.yumu.hexie.integration.baidu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.MyHttpClient;
import com.yumu.hexie.integration.baidu.vo.BaiduAddress;
import com.yumu.hexie.integration.baidu.vo.BaiduDetail;
import com.yumu.hexie.integration.baidu.vo.BaiduLocation;
import com.yumu.hexie.integration.baidu.vo.BaiduQueryResult;
import com.yumu.hexie.integration.wuye.util.HttpUtil;

public class BaiduMapUtil {

	private static final Logger Log = LoggerFactory.getLogger(BaiduMapUtil.class);

	public static List<BaiduAddress> queryAddress(String region,String keyword) {
		String reqUrl = "http://api.map.baidu.com/place/v2/search?ak="+
				ConstantBaidu.MAPKEY
	    	+"&output=json&query="+keyword
	    	+"&page_size=10&page_num=0&scope=2&region="
	    	+region;
		HttpGet get = new HttpGet(reqUrl);
		String resp;
		try {
			Log.error("REQ:" + reqUrl);
			resp = MyHttpClient.getStringFromResponse(MyHttpClient.execute(get),"UTF-8");
			Log.error("RESP:" + resp);
			BaiduQueryResult v =(BaiduQueryResult)JacksonJsonUtil.jsonToBean(resp, BaiduQueryResult.class);
			if(v.getStatus() == 0) {
				return v.getResults();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<BaiduAddress>();
	}
	
	public static BaiduDetail queryLocation(String keyword) {
		String reqUrl = "http://api.map.baidu.com/geocoder/v2/?address="+keyword
				+ "&output=json&ak="+ConstantBaidu.MAPKEY;
		HttpGet get = new HttpGet(reqUrl);
		String resp;
		try {
			resp = MyHttpClient.getStringFromResponse(MyHttpClient.execute(get),"UTF-8");
			Log.error("RESP:" + resp);
			BaiduLocation v =(BaiduLocation)JacksonJsonUtil.jsonToBean(resp, BaiduLocation.class);
			if(v.getStatus() == 0) {
				return v.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 坐标转换（百度）
	 */
	public static String findByCoordinateGetBaidu(String coordinate) {
		// TODO Auto-generated method stub
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("coords", coordinate);//（经度，纬度）
		paramsMap.put("from", "1");//1：GPS设备获取的角度坐标，WGS84坐标;
		paramsMap.put("to", "5");//5：bd09ll(百度经纬度坐标);
		paramsMap.put("ak", ConstantBaidu.MAPKEY);
		String s = HttpUtil.doGet("http://api.map.baidu.com/geoconv/v1/", paramsMap,"UTF-8");
		try {
			JSONObject json = new JSONObject(s);
			String x = "";
			String y = "";
			if(json.has("result")) {
				String result = json.get("result").toString();
				JSONArray jsonarray = new JSONArray(result);
				if(jsonarray.length()>0) {
					String resultjson = jsonarray.get(0).toString();
					JSONObject cn = new JSONObject(resultjson);
					if(cn.has("x")) x = cn.get("x").toString();
					if(cn.has("y")) y = cn.get("y").toString();
				}
			}
			return x+","+y;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过坐标获取市
	 */
	public static String findByBaiduGetCity(String coordinate) {
		// TODO Auto-generated method stub
		if(coordinate==null||"".equals(coordinate.trim())) {
			return "";
		}
		String[] coors = coordinate.split(",");
		String lng = coors[0];
		String lat = coors[1];
		coordinate = lat+","+lng;
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("location", coordinate);//lat<纬度>,lng<经度>
		paramsMap.put("latest_admin", "1");
		paramsMap.put("pois", "1");
		paramsMap.put("output", ConstantBaidu.OUTPUT);
		paramsMap.put("ak", ConstantBaidu.MAPKEY);
		String s = HttpUtil.doGet("http://api.map.baidu.com/geocoder/v2/", paramsMap,"UTF-8");
		try {
			JSONObject result = new JSONObject(s);
			String city = "";
			if(result.has("result")) {
				String resultjson = result.get("result").toString();
				JSONObject json = new JSONObject(resultjson);
				if(json.has("addressComponent")) {
					String address = json.get("addressComponent").toString();
					JSONObject addressjson = new JSONObject(address);
					if(addressjson.has("city")) {
						city = addressjson.get("city").toString();//市
					}
				}
			}
			return city;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String args[]) {
		System.out.println(findByBaiduGetCity(findByCoordinateGetBaidu("121.4737,31.23037")));
	}
}
