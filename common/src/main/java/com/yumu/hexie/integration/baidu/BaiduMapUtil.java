package com.yumu.hexie.integration.baidu;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.MyHttpClient;
import com.yumu.hexie.integration.baidu.vo.BaiduAddress;
import com.yumu.hexie.integration.baidu.vo.BaiduDetail;
import com.yumu.hexie.integration.baidu.vo.BaiduLocation;
import com.yumu.hexie.integration.baidu.vo.BaiduQueryResult;

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
	
	public static void main(String args[]) {
		//queryAddress("上海", "中华园");
		queryLocation("上海上海长宁美兰湖");
	}
}
