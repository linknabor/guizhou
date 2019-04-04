package com.yumu.hexie.integration.amap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.MyHttpClient;
import com.yumu.hexie.integration.amap.req.DataCreateReq;
import com.yumu.hexie.integration.amap.resp.DataCreateResp;
import com.yumu.hexie.integration.amap.vo.AmapQueryResult;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.distribution.region.AmapAddress;

public class AmapUtil {

	private static final Logger Log = LoggerFactory.getLogger(AmapUtil.class);
	//高德云图基地址
	private static final String BASE_YUNTU_URL = "http://yuntuapi.amap.com";
    //本地检索
    private static final String DATASEARCH_URL="/datasearch/local";
    //本地检索
    private static final String AROUOND_URL="/datasearch/around";
    
	//id搜索
	private static final String DATASEARCH_ID_URL="/datasearch/id";
	//按条件检索数据地址
	private static final String DATAMANAGE_URL="/datamanage/data/list";
	//创建单条数据
	private static final String DATAMANAGE_DATA_CREATE_URL="/datamanage/data/create";
	//around搜索
	private static final String DATASEARCH_AROUND="/datasearch/around";
	

	public static List<AmapAddress> findByRegionAndName(String city, String region,String name) {
	    String reqUrl = BASE_YUNTU_URL + DATASEARCH_URL
	            + "?tableid=" + ConstantAmap.AMAPTABLEID
	            + "&city=" + city
	            + "&filter=_name:" + name+"+region:"+region
	            + "&keywords=" + name
	            + "&limit=" + ConstantAmap.DEFAULTLIMIT
	            + "&page=" + ConstantAmap.DEFAULTPAGE
	            + "&key=" + ConstantAmap.AMAPCLOUDKEY;
	        HttpGet get = new HttpGet(reqUrl);
	        String resp;
	        try {
	            resp = MyHttpClient.getStringFromResponse(MyHttpClient.execute(get),"UTF-8");
	            AmapQueryResult v =(AmapQueryResult)JacksonJsonUtil.jsonToBean(resp, AmapQueryResult.class);
	            if(v.getStatus() == 1) {
	                return v.getDatas();
	            }else if(v.getStatus() == 0){
	                Log.error("Amap本地搜索错误，info:" + v.getInfo());
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return new ArrayList<AmapAddress>();
	}
	
	//本地搜索
	public static List<AmapAddress> dataSearchLocal(String city,String keyword) {
		String reqUrl = BASE_YUNTU_URL + DATASEARCH_URL
			+ "?tableid=" + ConstantAmap.AMAPTABLEID
	    	+ "&city=" + city
	    	+ "&keywords=" + keyword
	    	+ "&limit=" + ConstantAmap.DEFAULTLIMIT
	    	+ "&page=" + ConstantAmap.DEFAULTPAGE
	    	+ "&key=" + ConstantAmap.AMAPCLOUDKEY;
		HttpGet get = new HttpGet(reqUrl);
		String resp;
		try {
			resp = MyHttpClient.getStringFromResponse(MyHttpClient.execute(get),"UTF-8");
			AmapQueryResult v =(AmapQueryResult)JacksonJsonUtil.jsonToBean(resp, AmapQueryResult.class);
			if(v.getStatus() == 1) {
				return v.getDatas();
			}else if(v.getStatus() == 0){
				Log.error("Amap本地搜索错误，info:" + v.getInfo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<AmapAddress>();
	}
	//经纬度搜索
    public static List<AmapAddress> around(double lon,double lat) {
        String reqUrl = BASE_YUNTU_URL + AROUOND_URL
            + "?tableid=" + ConstantAmap.AMAPTABLEID
            + "&center="+String.format("%.6f", lon)+","+String.format("%.6f", lat)
            + "&limit=" + ConstantAmap.DEFAULTLIMIT
            + "&page=" + ConstantAmap.DEFAULTPAGE
            + "&key=" + ConstantAmap.AMAPCLOUDKEY;
        HttpGet get = new HttpGet(reqUrl);
        String resp;
        try {
            resp = MyHttpClient.getStringFromResponse(MyHttpClient.execute(get),"UTF-8");
            AmapQueryResult v =(AmapQueryResult)JacksonJsonUtil.jsonToBean(resp, AmapQueryResult.class);
            if(v.getStatus() == 1) {
                return v.getDatas();
            }else if(v.getStatus() == 0){
                Log.error("Amap本地搜索错误，info:" + v.getInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AmapAddress>();
    }
	//按照ID搜索
	public static AmapAddress dataSearchId(long _id) {
		String reqUrl = BASE_YUNTU_URL + DATASEARCH_ID_URL
			+ "?tableid=" + ConstantAmap.AMAPTABLEID
	    	+ "&_id=" + _id
	    	+ "&key=" + ConstantAmap.AMAPCLOUDKEY;
		HttpGet get = new HttpGet(reqUrl);
		String resp;
		try {
			resp = MyHttpClient.getStringFromResponse(MyHttpClient.execute(get),"UTF-8");

            Log.info("Amap resp " + resp);
			AmapQueryResult v =(AmapQueryResult)JacksonJsonUtil.jsonToBean(resp, AmapQueryResult.class);
			if(v.getStatus() == 1) {
				for(AmapAddress addr: v.getDatas()){
					return addr; //根据_id取值只有1个AmapAddress
				}
			}else if(v.getStatus() == 0){
				Log.error("Amap按照ID搜索：info=" + v.getInfo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//按条件检索数据
	public static List<AmapAddress> dataManageDataList(String name) {
		String reqUrl = BASE_YUNTU_URL + DATAMANAGE_URL
			+ "?tableid=" + ConstantAmap.AMAPTABLEID
	    	+ "&filter=_name:" + name
	    	+ "&limit=" + ConstantAmap.DEFAULTLIMIT
	    	+ "&page=" + ConstantAmap.DEFAULTPAGE
	    	+ "&key=" + ConstantAmap.AMAPCLOUDKEY;
		HttpGet get = new HttpGet(reqUrl);
		String resp;
		try {
			Log.info("REQ:" + reqUrl);
			resp = MyHttpClient.getStringFromResponse(MyHttpClient.execute(get),"UTF-8");
			Log.info("RESP:" + resp);
			AmapQueryResult v =(AmapQueryResult)JacksonJsonUtil.jsonToBean(resp, AmapQueryResult.class);
			if(v.getStatus() == 1) {
				return v.getDatas();
			}else if(v.getStatus() == 0){
				Log.error("Amap按照条件搜索：info=" + v.getInfo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<AmapAddress>();
	}

	//创建单条数据
	public static DataCreateResp dataManageDataCreate(DataCreateReq dataCreateReq) {
		String dataStr = null;
		try {
			dataStr = JacksonJsonUtil.beanToJson(dataCreateReq);
		} catch (JSONException e) {
			Log.error("Amap 创建单条数据转换(beanToJson)失败！！！");
		}
		System.out.println("dataStr:"+dataStr);
		Map<String,String> map = new HashMap<String,String>();
		map.put("key", ConstantAmap.AMAPCLOUDKEY);
		map.put("tableid", ConstantAmap.AMAPTABLEID);
		map.put("loctype", "2"); //以地址的方式存储
		map.put("data", dataStr);

		DataCreateResp resp = (DataCreateResp)httpPost(BASE_YUNTU_URL + DATAMANAGE_DATA_CREATE_URL, map, DataCreateResp.class);
		if(! resp.isSuccess()){
			System.out.println("Amap插入单条数据，info="+resp.getInfo());
			return null;
		} else {
			System.out.println("Amap插入单条数据，info="+resp.getInfo());
		}
		return resp;
	}

	private static Object httpPost(String reqUrl,Map<String,String> content, Class c){
		String resp;
		try {
			// 建立HttpPost对象
	        HttpPost httppost = new HttpPost(reqUrl);
	        // 建立NameValuePair数组，用于存储请求的参数
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        for (Map.Entry<String, String> entry : content.entrySet()) {
	            params.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
	        }
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			resp = MyHttpClient.getStringFromResponse(MyHttpClient.execute(httppost),"UTF-8");
			return JacksonJsonUtil.jsonToBean(resp, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	//按条件检索数据
    public static List<AmapAddress> query(int page,int pageSize) {
        String reqUrl = BASE_YUNTU_URL + DATAMANAGE_URL
            + "?tableid=" + ConstantAmap.AMAPTABLEID
            + "&limit=" + pageSize
            + "&page=" + page
            + "&key=" + ConstantAmap.AMAPCLOUDKEY;
        HttpGet get = new HttpGet(reqUrl);
        String resp;
        try {
            Log.error("REQ:" + reqUrl);
            resp = MyHttpClient.getStringFromResponse(MyHttpClient.execute(get),"UTF-8");
            Log.error("RESP:" + resp);
            AmapQueryResult v =(AmapQueryResult)JacksonJsonUtil.jsonToBean(resp, AmapQueryResult.class);
            if(v.getStatus() == 1) {
                return v.getDatas();
            }else if(v.getStatus() == 0){
                Log.error("Amap按照条件搜索：info=" + v.getInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("高德查询时报",e);
        }
        return new ArrayList<AmapAddress>();
    }
	public static void main(String args[]) throws InterruptedException, JSONException {
//		dataManageDataList("中山");
					
		//dataManageDataCreate(new DataCreateReq("航空航天", null,null,"上海市闵行区航空航天","上海市", "闵行区"));
//		Thread.sleep(1000);
//		dataSearchLocal("上海市", "美兰湖");
//		dataSearchId(21);
//		testLocation();
	    //List<AmapAddress> addrs = query(1,10);
//	    for(AmapAddress addr : addrs){
//	        System.out.println(JacksonJsonUtil.beanToJson(addr));
//	    }
        //List<AmapAddress> addrs = around(121.52076,31.145576);
        List<AmapAddress> addrs = query(1, 21);
	       for(AmapAddress addr : addrs){
	          System.out.println(JacksonJsonUtil.beanToJson(addr));
	      }
	       addrs = 
	               dataManageDataList("美兰湖");
           for(AmapAddress addr : addrs){
              System.out.println(JacksonJsonUtil.beanToJson(addr));
          }
	    //System.out.println(String.format("%.6f", 1123.222349933d));
	}
	
	public static void testLocation(){
		AmapAddress amapAddr = dataSearchId(21);
			System.out.println(amapAddr.getLat());
            System.out.println(amapAddr.getLon());
	}
	
	//通过坐标查找周围小区
	public static List<AmapAddress> queryAroundByCoordinate(double longitude, double latitude){
		String reqUrl = BASE_YUNTU_URL + DATASEARCH_AROUND
	            + "?tableid=" + ConstantAmap.AMAPTABLEID
	            + "&center=" + longitude + "," + latitude
	            + "&filter=regiontype:" + ModelConstant.REGION_XIAOQU
	            + "&sortrule=_distance"
	            + "&limit=10"
	            + "&key=" + ConstantAmap.AMAPCLOUDKEY;
	    HttpGet get = new HttpGet(reqUrl);
	    String resp;
	    try {
	        Log.info("REQ:" + reqUrl);
	        resp = MyHttpClient.getStringFromResponse(MyHttpClient.execute(get),"UTF-8");
	        Log.info("RESP:" + resp);
	        AmapQueryResult v =(AmapQueryResult)JacksonJsonUtil.jsonToBean(resp, AmapQueryResult.class);
	        if(v.getStatus() == 1) {
	            return v.getDatas();
	        }else if(v.getStatus() == 0){
	            Log.error("Amap通过坐标查找周围小区：info=" + v.getInfo());
	        }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    return new ArrayList<AmapAddress>();
	}
}
