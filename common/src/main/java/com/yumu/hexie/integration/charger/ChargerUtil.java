package com.yumu.hexie.integration.charger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Properties;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.hibernate.bytecode.buildtime.spi.ExecutionException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.integration.charger.vo.ChargerType;
import com.yumu.hexie.integration.charger.vo.PayStatusResult;
import com.yumu.hexie.integration.wuye.WuyeUtil;
import com.yumu.hexie.integration.wuye.resp.BaseResult;
import com.yumu.hexie.integration.wuye.vo.WechatPayInfo;

public class ChargerUtil {

	private static final Logger Log = LoggerFactory.getLogger(ChargerUtil.class);
	
	private static String REQUEST_BACKMNG_ADDRESS = "http://www.e-shequ.com/mobileInterface/charger/";
	private static String SYSTEM_NAME;
	private static Properties props = new Properties();
	
	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("wechat.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		REQUEST_BACKMNG_ADDRESS = props.getProperty("requestChargerUrl");
		SYSTEM_NAME = props.getProperty("sysName");
	}
	
	private static final String MARGER_ADD_USE_URL = "addChargerUseSDO.do?openId=%s&phone=%s&sn=%s&sectId=%s&from_sys=%s";
	private static final String CHARGER_APPKEY = "getChargerAppKeySDO.do?openId=%s&phone=%s&sn=%s";
	private static final String MARGER_TYPE_URL = "getChargerTypeSDO.do";
	private static final String MARGER_PAY_URL = "margerPaySDO.do?user_id=%s&phone=%s&openId=%s&money=%s&from_sys=%s";
	private static final String MARGER_PAY_STATUS_URL = "margerPayStatusSDO.do?openId=%s&phone=%s&user_id=%s&trade_water_id=%s&package=%s";
	
	// 1.云充用户创建
	@SuppressWarnings("unchecked")
	public static BaseResult<String> saveChargerUser(String openId, String phone, String sn, String sectId){
		String url = REQUEST_BACKMNG_ADDRESS + String.format(MARGER_ADD_USE_URL, openId, phone, sn, sectId, SYSTEM_NAME);
		return (BaseResult<String>)httpsRequest(url,String.class);
	}
	
	//2.获取云充AppKey
	public static String getAppKeyUrl(String openId, String phone, String sn){
		String url = REQUEST_BACKMNG_ADDRESS + String.format(CHARGER_APPKEY, openId, phone, sn);
		return (String)httpsRequest(url,String.class).getData();
	}
	
	/*3.查询充值类别*/
	@SuppressWarnings("unchecked")
	public static ChargerType getChargerType() throws ValidationException
	{
		String url = REQUEST_BACKMNG_ADDRESS + MARGER_TYPE_URL;
		BaseResult baseResult = httpsRequest(url,ChargerType.class);
		if(baseResult.isSuccess())
		{
			return  (ChargerType) baseResult.getData();
			
		}else
		{
			throw new ValidationException(baseResult.getData().toString());
		}
	}
	
	/*4.充值付款 */
	@SuppressWarnings("unchecked")
	public static BaseResult<WechatPayInfo> getChargerPay(String user_id, String phone, String openId, String money) throws ValidationException
	{
		String url = REQUEST_BACKMNG_ADDRESS + String.format(MARGER_PAY_URL, user_id, phone, openId, money, SYSTEM_NAME);
		
		BaseResult baseResult = httpsRequest(url,WechatPayInfo.class);
		if (!baseResult.isSuccess()) {
			throw new ValidationException(baseResult.getData().toString());
		}
		return (BaseResult<WechatPayInfo>)httpsRequest(url,WechatPayInfo.class);
	}
	
	/*5.查询支付情况*/
	public static PayStatusResult noticeChargerPay(String openId, String phone, String userId, String tradeWaterId, String packageId)
	{
		String url = REQUEST_BACKMNG_ADDRESS + String.format(MARGER_PAY_STATUS_URL, openId, phone, userId, tradeWaterId, packageId);
		return (PayStatusResult) httpsRequest(url,PayStatusResult.class).getData();
	}
	
	private static BaseResult httpsRequest(String requestUrl, Class c){
		HttpClient httpClient = null;
		Log.error("REQ:" + requestUrl);
		String err_code = null;
		String err_msg = null;
		try
		{
			httpClient = createSSLInsecureClient();
			HttpGet request = new HttpGet(requestUrl);
			request.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36");
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				String resp = EntityUtils.toString(response.getEntity(), "UTF-8");
				Log.error("RESP:" + resp);
				
				if(requestUrl.indexOf("margerPaySDO.do")>=0) {
					resp = resp.replace("package", "packageValue");
					Map respMap = JacksonJsonUtil.json2map(resp);
					String result = (String)respMap.get("result");
					if (!"00".equals(result)) {
						err_msg = (String)respMap.get("err_msg");
						err_code = result;
						throw new ExecutionException(err_code+", " +err_msg);
					}
				}
				
				return WuyeUtil.jsonToBeanResult(resp, c);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			Log.error("err msg :" + e.getMessage());
		}
		BaseResult r= new BaseResult();
		r.setResult("99");
		return r;
	}
	
	public static CloseableHttpClient createSSLInsecureClient()
	{
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain,String authType) throws CertificateException {
                    return true;
                }
            }).build();
		    sslsf = new SSLConnectionSocketFactory(sslContext);
		    return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}
	
	/**
	 * 接收请求流转json
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static Map<String,Object> recvStreamToJson(HttpServletRequest request) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        return JacksonJsonUtil.json2map(sb.toString());
        
	}
	
	public static void errorResponseT(HttpServletResponse response, Exception e)
	{
		JSONObject json = new JSONObject();
		try {
			json.put("result", "99");
			json.put("data", new JSONObject());
			json.put("err_msg", e.getMessage());
			Log.info("error request 【"+json+"】");
			response.getWriter().write(json.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		getChargerType();
	}
}