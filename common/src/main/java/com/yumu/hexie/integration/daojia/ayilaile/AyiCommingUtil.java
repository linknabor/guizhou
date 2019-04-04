package com.yumu.hexie.integration.daojia.ayilaile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
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
import com.yumu.hexie.integration.daojia.ayilaile.req.PtOrderReq;
import com.yumu.hexie.integration.daojia.ayilaile.req.ServiceOrderReq;
import com.yumu.hexie.integration.daojia.ayilaile.resp.AgentInfo;
import com.yumu.hexie.integration.daojia.ayilaile.resp.DictionaryResp;
import com.yumu.hexie.integration.daojia.ayilaile.resp.PtOrderResp;
import com.yumu.hexie.integration.daojia.ayilaile.resp.ServiceOrderResp;
import com.yumu.hexie.integration.daojia.ayilaile.vo.DictionaryVO;

public class AyiCommingUtil {
//	
//	月嫂薪金范围：F65EFBE6-861A-47E3-B30C-54D9437777E1
//	育儿嫂薪金范围：F34377BE-6278-43EB-AF02-CE42FADBBACE
//	家务员薪金范围：AC6D6579-C91F-4C11-A85B-D5E77278D3F7
//	包月小时工薪金范围：C07040DB-846A-499D-B724-3EE1CF49ED30
//	老年陪护薪金范围：C0D2AABE-9898-445E-B176-7C485F831FCD	
	
//	月嫂：7DE4C552-70EC-47B0-BEBF-AA7EC0236A4D
//	育儿嫂：43BC8456-76DB-4229-9241-D257FF0D04BB
//	家务员：6CFCD891-D483-4754-AA56-3276AC3AA21F
//	包月小时工：1A054B30-F8B4-45E0-945A-D7D19E9A3AFE
//	老年陪护：A93B31AF-B471-4372-B1D6-7398F2AD27A5
	public static final String XINJIN_YUESAO = "F65EFBE6-861A-47E3-B30C-54D9437777E1";
	public static final String XINJIN_YUER = "F34377BE-6278-43EB-AF02-CE42FADBBACE";
	public static final String XINJIN_JIAWUYUAN = "AC6D6579-C91F-4C11-A85B-D5E77278D3F7";
	public static final String XINJIN_BAOYUE = "C07040DB-846A-499D-B724-3EE1CF49ED30";
	public static final String XINJIN_LAONIAN = "C0D2AABE-9898-445E-B176-7C485F831FCD";

	public static final String ORDER_YUESAO = "7DE4C552-70EC-47B0-BEBF-AA7EC0236A4D";
	public static final String ORDER_YUER = "43BC8456-76DB-4229-9241-D257FF0D04BB";
	public static final String ORDER_JIAWUYUAN = "6CFCD891-D483-4754-AA56-3276AC3AA21F";
	public static final String ORDER_BAOYUE = "1A054B30-F8B4-45E0-945A-D7D19E9A3AFE";
	public static final String ORDER_LAONIAN = "A93B31AF-B471-4372-B1D6-7398F2AD27A5";

	
	private static final Logger Log = LoggerFactory.getLogger(AyiCommingUtil.class);
//	private static final String BASE_URL = "http://testserviceapp.ayilaile.com/ws/AgentService.asmx"; //测试服务器
	private static final String BASE_URL = "http://serviceapp.ayilaile.com/ws/AgentService.asmx";
	private static final String AGENT_ID = "E944C97B-B32F-408A-9842-276B6613B81F";
	//接口地址
	//（编号：401）查询代理商信息
	private static final String GET_AGENTINFO_URL="/GetAgentInfo";//获取?user_id=%s
	//（编号：402）临时小时工订单提交
	private static final String ADD_PTORDER_URL="/AddPtOrder";//
	private static final String ADD_PTORDER_PARAM="dtWorkTime=%s&guidAgentId=%s&nPayMoney=%s&nWorkHourLength=%s&strMobile=%s&strName=%s&strWorkAddr=%s&strWorkDetail=%s";
	//（编号：405）全局字典---获得薪金范围
	private static final String GET_LISTDICTIONARY_URL="/GetListDictionary";
	private static final String GET_LISTDICTIONARY_PARAM="guidAgentId=%s&guidDictionaryGroupID=%s&nOrderByType=%s";
	//（编号：406）提交服务订单
	private static final String ADD_SERVICEORDER_URL="/AddServiceOrder";
	private static final String ADD_SERVICEORDER_PARAM="dtPlanStartDate=%s&guidAgentId=%s&guidSalaryRangeID=%s&guidServiceTypeID=%s"
			+ "&strMobile=%s&strName=%s&strOtherNeed=%s&strPlanStartTimeHour=%s&strPlanStartTimeMimute=%s"
			+ "&strServiceAddr=%s&strWorkDuration=%s&strWorkFrequency=%s";

	private static AgentInfo agentInfo = null;

	private static AgentInfo getAgentInfo(){
		if(agentInfo != null) {
			return agentInfo;
		}

		Map<String,String> map = new HashMap<String, String>();
		map.put("guidAgentId", AGENT_ID);
		agentInfo = (AgentInfo)httpPost(BASE_URL+GET_AGENTINFO_URL, map, AgentInfo.class);
		return agentInfo;
	}
	
	public static List<DictionaryVO> getListDictionary(String groupId) {
		String params = String.format(GET_LISTDICTIONARY_PARAM,AGENT_ID,groupId,"1");
		String url = BASE_URL+GET_LISTDICTIONARY_URL+"?"
				+params+"&sign=" 
				+DigestUtils.md5Hex(params.toLowerCase()+getAgentInfo().getStrToken());
		DictionaryResp resp = (DictionaryResp)httpGet(url,DictionaryResp.class);
		if(resp.isSuccess()){
			return resp.getData();
		} else {
			return null;
		}
	}
	
	public static PtOrderResp addPtOrder(PtOrderReq order) {
		String params = String.format(ADD_PTORDER_PARAM,order.getDtWorkTime(),AGENT_ID,order.getnPayMoney(),
				order.getnWorkHourLength(),order.getStrMobile(),order.getStrName(),order.getStrWorkAddr(),order.getStrWorkDetail());
		Map<String,String> map = new HashMap<String, String>();
		map.put("dtWorkTime", order.getDtWorkTime());
		map.put("guidAgentId", AGENT_ID);
		map.put("nPayMoney", order.getnPayMoney());
		map.put("nWorkHourLength", order.getnWorkHourLength());
		map.put("strMobile", order.getStrMobile());
		map.put("strName", order.getStrName());
		map.put("strWorkAddr", order.getStrWorkAddr());
		map.put("strWorkDetail", order.getStrWorkDetail());
		map.put("sign", DigestUtils.md5Hex(params.toLowerCase()+getAgentInfo().getStrToken()));
		String url = BASE_URL+ADD_PTORDER_URL;
		return (PtOrderResp)httpPost(url,map,PtOrderResp.class);
	}
	
	public static ServiceOrderResp addServiceOrder(ServiceOrderReq order) {
		String params = String.format(ADD_SERVICEORDER_PARAM,order.getDtPlanStartDate(),AGENT_ID,order.getGuidSalaryRangeID(),order.getGuidServiceTypeID(),
				order.getStrMobile(),order.getStrName(),order.getStrOtherNeed(),order.getStrPlanStartTimeHour()
				,order.getStrPlanStartTimeMimute(),order.getStrServiceAddr(),order.getStrWorkDuration(),order.getStrWorkFrequency());
		Map<String,String> map = new HashMap<String, String>();
		map.put("dtPlanStartDate", order.getDtPlanStartDate());
		map.put("guidAgentId", AGENT_ID);
		map.put("guidSalaryRangeID", order.getGuidSalaryRangeID());
		map.put("guidServiceTypeID", order.getGuidServiceTypeID());
		map.put("strMobile", order.getStrMobile());
		map.put("strName", order.getStrName());
		map.put("strOtherNeed", order.getStrOtherNeed());
		map.put("strPlanStartTimeHour", order.getStrPlanStartTimeHour());
		map.put("strPlanStartTimeMimute", order.getStrPlanStartTimeMimute());
		map.put("strServiceAddr", order.getStrServiceAddr());
		map.put("strWorkDuration", order.getStrWorkDuration());
		map.put("strWorkFrequency", order.getStrWorkFrequency());
		map.put("sign", DigestUtils.md5Hex(params.toLowerCase()+getAgentInfo().getStrToken()));
		String url = BASE_URL+ADD_SERVICEORDER_URL;
		return (ServiceOrderResp)httpPost(url,map,ServiceOrderResp.class);
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
			Log.error("REQ:" + reqUrl);
			Log.error("REQ:" + JacksonJsonUtil.beanToJson(content));
			resp = MyHttpClient.getStringFromResponse(MyHttpClient.execute(httppost),"UTF-8");
			Log.error("RESP:" + resp);
			
			String json = JacksonJsonUtil.xml2json(resp).replace("{\"\":", "");
			
			json = json.substring(1, json.length()-2).replaceAll("\\\\\"", "\"");

			json = json.replace("\"Data\":", "\"data\":");
			Log.error("RESP:" + json);
			return JacksonJsonUtil.jsonToBean(json, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Object httpGet(String reqUrl, Class c) {

		HttpGet get = new HttpGet(reqUrl);
		String resp;
		try {
			Log.error("REQ:" + reqUrl);
			resp = MyHttpClient.getStringFromResponse(
					MyHttpClient.execute(get), "UTF-8");
			Log.error("RESP:" + resp);
			String json = JacksonJsonUtil.xml2json(resp).replace("{\"\":", "");
			json = json.substring(1, json.length() - 2).replaceAll("\\\\\"",
					"\"");
			json = json.replace("\"Data\":", "\"data\":");
			return JacksonJsonUtil.jsonToBean(json, c);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String args[]) throws JSONException {
		//getAgentInfo();
		//System.out.println(GetListDictionary(getListDictionary));
		System.out.println(getListDictionary(XINJIN_BAOYUE));

		//System.out.println(addPtOrder(new PtOrderReq("13333333333",
		//		"上海社区", "2015-02-02T12:12:12", "上海交大", "100", "很好很好", "12")));
		//System.out.println(addPtOrder(new PtOrderReq("13333333333",
		//		"上海社区", "2015-02-02T12:12:12", "", "100", "", "12")));
		//getListDictionary("7DE4C552-70EC-47B0-BEBF-AA7EC0236A4D").get(0).get;
		//System.out.println(addServiceOrder(new ServiceOrderReq("13300000000", "abc", "1A054B30-F8B4-45E0-945A-D7D19E9A3AFE", "DCFC6709-FAFD-442E-953A-6C872152A554", 
		//		"2015-01-01", "xxx", "xx", "12", "23", "1", "3")));
		//System.out.println(JacksonJsonUtil.beanToJson(addServiceOrder(new ServiceOrderReq("13300000000", "abc", "1A054B30-F8B4-45E0-945A-D7D19E9A3AFE", "DCFC6709-FAFD-442E-953A-6C872152A554", 
		//		"2015-02-02T12:12:12", "2", "3", "4", "5", "6", "7"))));

		//System.out.println(addServiceOrder(new ServiceOrderReq("1", "", "1", "1", 
		//		"1", "1", "1", "1", "1", "1", "1")));
	}
}
