package com.yumu.hexie.integration.wuye.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import com.yumu.hexie.service.exception.BizValidateException;

/**
 * HttpClient 工具包
 * @author Jackie
 *
 */
public class HttpUtil {

	private static final Log logger = LogFactory.getLog(HttpUtil.class);
	
	private static HttpUtil instance = null;
	private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 50000;	//50秒
    private String key_store_file;
    private String key_store_pass;
    private String trust_store_file;
    private String trust_store_pass;
    
    public static final String codeFormat = "UTF-8";
    
    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }
    
    public HttpUtil(String key_store_file, String key_store_pass, String trust_store_file, String trust_store_pass) {
    	this.key_store_file = key_store_file;
    	this.key_store_pass = key_store_pass;
    	this.trust_store_file = trust_store_file;
    	this.trust_store_pass = trust_store_pass;
    }
    
    public static HttpUtil getInstance(String key_store_file, String key_store_pass, String trust_store_file, String trust_store_pass) {
    	if (instance==null) {
			return new HttpUtil(key_store_file, key_store_pass, trust_store_file, trust_store_pass);
		} else {
			return instance;
		}
    }
    
    /**
     * 发送 GET 请求
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, String> params, String codeFormat) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        if (params != null) {
        	for (String key : params.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                param.append(key).append("=").append(params.get(key));
                i++;
            }
		}
        
        apiUrl += param;
        String result = null;
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        try {
        	
        	HttpGet httpGet = new HttpGet(apiUrl);
        	HttpResponse httpResponse = httpCilent.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            	result = EntityUtils.toString(httpResponse.getEntity());//获得返回的结果
            } else {
            	throw new BizValidateException("请求失败，response statusCode: " + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
        	throw new BizValidateException(e.getMessage());
        }finally {
        	try {
                 httpCilent.close();
             } catch (IOException e) {
            	 throw new BizValidateException(e.getMessage());
             }
		}
        return result;
    }

    /**
     * 发送POST 请求 支持Map和JsonObject、xml
     * @param apiUrl
     * @param obj
     * @param codeFormat
     * @return
     */
    @SuppressWarnings("unchecked")
	public static String doPost(String apiUrl, Object obj, String codeFormat) {
        String httpStr = null;
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setConfig(requestConfig);
        
        logger.info("request obj type : " +obj.getClass());
        
        try {
        	if (obj instanceof HashMap || obj instanceof TreeMap) {//键值形式
        		httpPost.setEntity(new UrlEncodedFormEntity(assembleRequestParams((Map<String,String>)obj), Charset.forName(codeFormat)));
        	} else if(obj instanceof JSONObject) {//json形式
        		 StringEntity stringEntity = new StringEntity(obj.toString(),codeFormat);//解决中文乱码问题
                 stringEntity.setContentType("application/json");
                 httpPost.setEntity(stringEntity);
				 httpPost.setHeader("Accept", "application/json");
        	} else {
        		boolean flag = isXml(obj.toString());
        		if (flag) { //xml
        			StringEntity stringEntity = new StringEntity(obj.toString(), codeFormat);//解决中文乱码问题
        			stringEntity.setContentType("text/xml");
        			httpPost.setEntity(stringEntity);
				}else {
					StringEntity stringEntity = new StringEntity(obj.toString(), codeFormat);//解决中文乱码问题
        			stringEntity.setContentType("text/*");
        			httpPost.setEntity(stringEntity);
				}
    		}
        	HttpResponse response = httpCilent.execute(httpPost);
        	if(response != null){ 
        		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
        			httpStr = EntityUtils.toString(response.getEntity());
	        	} else {
	        		throw new BizValidateException("亲求失败，response code :" + response.getStatusLine().getStatusCode());
	        	}
        	}
        } catch (Exception e) {
        	if (e instanceof BizValidateException)
				throw (BizValidateException) e;
			else
				throw new BizValidateException(e.getMessage());
        } finally {
        	try {
        		if(httpCilent != null){
        			httpCilent.close(); //释放资源
                }
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
        return httpStr;
    }
    
    /**
     * 双向认证 SSL POST 请求 支持Map和JsonObject、xml
     * @param apiUrl
     * @param obj
     * @param codeFormat
     * @return
     */
    @SuppressWarnings("unchecked")
	public String doPostTwoWaySSL(String apiUrl, Object obj, String codeFormat) {
    	CloseableHttpClient httpClient = getClientTwoWaySSLAuth();
        HttpPost httpPost = new HttpPost(apiUrl);
        String httpStr = null;
        
        try {
        	if (obj instanceof HashMap || obj instanceof TreeMap) {//键值形式
        		httpPost.setEntity(new UrlEncodedFormEntity(assembleRequestParams((Map<String,String>)obj), Charset.forName(codeFormat)));
    		} else if(obj instanceof JSONObject) {//json形式
    			StringEntity stringEntity = new StringEntity(obj.toString(),codeFormat);//解决中文乱码问题
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
    		} else {
    			try {
					DocumentHelper.parseText(obj.toString());//xml格式
					StringEntity stringEntity = new StringEntity(obj.toString(), codeFormat);//解决中文乱码问题
        			stringEntity.setContentType("text/xml");
        			httpPost.setEntity(stringEntity);
				} catch (DocumentException e) {
					throw new BizValidateException("无效的数据类型["+ obj.getClass()+"],只支持Map、JSONObject、XML");
				}
    		}
        	
        	HttpResponse response = httpClient.execute(httpPost);
        	if(response != null){ 
        		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
        			httpStr = EntityUtils.toString(response.getEntity(), codeFormat);
	        	} else {
	        		throw new BizValidateException("亲求失败，response code :" + response.getStatusLine().getStatusCode());
	        	}
        	}
        } catch (Exception e) {
        	if (e instanceof BizValidateException)
				throw (BizValidateException) e;
			else
				throw new BizValidateException(e.getMessage());
        } finally {
        	try {
        		if(httpClient != null){
        			httpClient.close(); //释放资源
                }
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
        return httpStr;
    }
    
    /**
     * 单向认证 SSL POST 请求 支持Map和JsonObject、xml
     * @param apiUrl
     * @param obj
     * @param codeFormat
     */
    @SuppressWarnings("unchecked")
	public String doPostOneWaySSL(String apiUrl, Object obj, String codeFormat) {
    	CloseableHttpClient httpClient = getClientOneWaySSLAuth();
        HttpPost httpPost = new HttpPost(apiUrl);
        String httpStr = null;

        try {
        	if (obj instanceof HashMap || obj instanceof TreeMap) {//键值形式
        		httpPost.setEntity(new UrlEncodedFormEntity(assembleRequestParams((Map<String,String>)obj), Charset.forName(codeFormat)));
    		} else if(obj instanceof JSONObject) {//json形式
    			StringEntity stringEntity = new StringEntity(obj.toString(),codeFormat);//解决中文乱码问题
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
    		} else {
    			try {
					DocumentHelper.parseText(obj.toString());//xml格式
					StringEntity stringEntity = new StringEntity(obj.toString(), codeFormat);//解决中文乱码问题
        			stringEntity.setContentType("text/xml");
        			httpPost.setEntity(stringEntity);
				} catch (DocumentException e) {
					throw new BizValidateException("无效的数据类型["+ obj.getClass()+"],只支持Map、JSONObject");
				}
    		}
        	
        	HttpResponse response = httpClient.execute(httpPost);
        	if(response != null){ 
        		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
        			httpStr = EntityUtils.toString(response.getEntity(), codeFormat);
	        	} else {
	        		throw new BizValidateException("亲求失败，response code :" + response.getStatusLine().getStatusCode());
	        	}
        	}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
        		if(httpClient != null){
        			httpClient.close(); //释放资源
                }
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
        return httpStr;
    }
    
    /**
     * 忽略认证
     * @param apiUrl
     * @param obj
     * @param codeFormat
     */
    @SuppressWarnings("unchecked")
	public static String doPostWeakSSL(String apiUrl, Object obj, String codeFormat) {
    	CloseableHttpClient httpClient = getClientWeakSSLAuth();
        HttpPost httpPost = new HttpPost(apiUrl);
        String httpStr = null;

        try {
        	if (obj instanceof HashMap || obj instanceof TreeMap) {//键值形式
        		httpPost.setEntity(new UrlEncodedFormEntity(assembleRequestParams((Map<String,String>)obj), Charset.forName(codeFormat)));
    		} else if(obj instanceof JSONObject) {//json形式
    			StringEntity stringEntity = new StringEntity(obj.toString(),codeFormat);//解决中文乱码问题
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
    		} else {
    			try {
					DocumentHelper.parseText(obj.toString());//xml格式
					StringEntity stringEntity = new StringEntity(obj.toString(), codeFormat);//解决中文乱码问题
        			stringEntity.setContentType("text/xml");
        			httpPost.setEntity(stringEntity);
				} catch (DocumentException e) {
					throw new BizValidateException("无效的数据类型["+ obj.getClass()+"],只支持Map、JSONObject");
				}
    		}
        	
        	HttpResponse response = httpClient.execute(httpPost);
        	if(response != null){ 
        		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
        			httpStr = EntityUtils.toString(response.getEntity(), codeFormat);
	        	} else {
	        		throw new BizValidateException("亲求失败，response code :" + response.getStatusLine().getStatusCode());
	        	}
        	}
        } catch (Exception e) {
        	if (httpPost!=null) {
        		httpPost.abort();
			}
        	throw new BizValidateException(e.getMessage());
        } finally {
        	if (httpPost!=null) {
        		httpPost.completed();
			}
        }
        return httpStr;
    }

    /**
     * 创建SSL安全连接（双向验证）
     * @param keystoreClassPath
     * @param certPassword
     * @return
     */
    private CloseableHttpClient getClientTwoWaySSLAuth() {
    	CloseableHttpClient httpsClientSSLAuth = null;
    	try {
    		KeyStore keyStore = KeyStore.getInstance("PKCS12");
	    	FileInputStream keyInstream = new FileInputStream(new File(key_store_file));
	    	
	    	KeyStore trustkeyStore = KeyStore.getInstance("JKS");
	    	FileInputStream trustInstream = new FileInputStream(new File(trust_store_file));
	    	try {
	    		keyStore.load(keyInstream, key_store_pass.toCharArray());
	    		trustkeyStore.load(trustInstream, trust_store_pass.toCharArray());
	    	}finally {
	    		if (keyInstream != null)
	    			keyInstream.close();
                if (trustInstream!=null)
                	trustInstream.close();
	    	}
	    	
	    	
	    	SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, key_store_pass.toCharArray()).loadTrustMaterial(trustkeyStore, new TrustSelfSignedStrategy()).build();
	    	SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null,  SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	    	Registry<ConnectionSocketFactory> registry = RegistryBuilder
	                .<ConnectionSocketFactory> create()
	                .register("http", PlainConnectionSocketFactory.INSTANCE)
	                .register("https", sslsf).build();
	    	
	    	httpsClientSSLAuth = HttpClients.custom().setConnectionManager(new PoolingHttpClientConnectionManager(registry)).build();
    	} catch (Exception e) {
    		throw new BizValidateException("new CloseableHttpClient is error :"+ e);
    	}
    	return httpsClientSSLAuth;
    }
    
    
    /**
     * 创建SSL安全连接（单向验证）
     * @param keystoreClassPath
     * @param certPassword
     * @return
     */
    private CloseableHttpClient getClientOneWaySSLAuth() {

    	CloseableHttpClient httpsClientSSLAuth = null;
    	try {
	    	KeyStore trustkeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
	    	FileInputStream trustInstream = new FileInputStream(new File(trust_store_file));
	    	try {
	    		trustkeyStore.load(trustInstream, trust_store_pass.toCharArray());
	    	}finally {
                if (trustInstream!=null)
                	trustInstream.close();
	    	}
	    	
    		//KeyManager选择证书证明自己的身份
	    	TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(trustkeyStore);
			TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
			
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustManagers, null);
			
	    	httpsClientSSLAuth = HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext)).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
    	
    	} catch (Exception e) {
    		throw new BizValidateException("new CloseableHttpClient is error :"+ e);
    	}
    	return httpsClientSSLAuth;
    }
    
    /**
     * 创建SSL安全连接（忽略验证）
     * @param keystoreClassPath
     * @param certPassword
     * @return
     */
    private static CloseableHttpClient getClientWeakSSLAuth() {

    	CloseableHttpClient httpsClientSSLAuth = null;
    	try {
    		//获得密匙库
	    	KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	    	
	    	//重写验证策略
	    	TrustStrategy strategy = new TrustSelfSignedStrategy(){
				@Override
				public boolean isTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					return true;
				}
	    	};
	    	SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(trustStore, strategy).build();
	    	
	    	/**
	    	 * 有需要的可以自己做验证
	    	 */
	    	X509HostnameVerifier verifier = new X509HostnameVerifier() {
				
				@Override
				public boolean verify(String paramString, SSLSession paramSSLSession) {
					
					return paramString.equals(paramSSLSession.getPeerHost());
				}
				
				@Override
				public void verify(String paramString, String[] paramArrayOfString1,
						String[] paramArrayOfString2) throws SSLException {
				}
				
				@Override
				public void verify(String paramString, X509Certificate paramX509Certificate)
						throws SSLException {
				}
				
				@Override
				public void verify(String paramString, SSLSocket paramSSLSocket)
						throws IOException {
				}
			};
	    	LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, verifier);
	    	
	    	httpsClientSSLAuth = HttpClients.custom().setSSLSocketFactory(sslSocketFactory).
	    			setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
	    	
    	} catch (Exception e) {
    		throw new BizValidateException("new CloseableHttpClient is error :"+ e);
    	}
    	return httpsClientSSLAuth;
    }
	
	/**
	 * 组装http请求参数
	 * 
	 * @param params
	 * @param menthod
	 * @return
	 */
	private static synchronized List<NameValuePair> assembleRequestParams(Map<String, String> params) {
		List<NameValuePair> nameValueList = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
            nameValueList.add(pair);
        }
		return nameValueList;
	}
	
	/**
	 * 发送接送数组
	 * @param apiUrl
	 * @param jsonarray
	 * @param codePage
	 * @return
	 */
	public static String doPostJsonArray(String apiUrl, JSONArray jsonarray, String codePage){
		
		 CloseableHttpClient httpClient = HttpClients.createDefault();
		 String httpStr = null;
		 HttpPost httpPost = new HttpPost(apiUrl);

		 try {
			 httpPost.setConfig(requestConfig);
			 StringEntity stringEntity = new StringEntity(jsonarray.toString(),codePage);//解决中文乱码问题
			 stringEntity.setContentEncoding(codePage);
			 stringEntity.setContentType("application/json");
			 httpPost.setEntity(stringEntity);
			 HttpResponse response = httpClient.execute(httpPost);
			 
			 if(response != null){ 
				 if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					 httpStr = EntityUtils.toString(response.getEntity(), codeFormat);
				 } else {
					 throw new BizValidateException("亲求失败，response code :" + response.getStatusLine().getStatusCode());
				 }
			 }
		 } catch (IOException e) {
			 throw new BizValidateException(e.getMessage());
		 } finally {
			 try {
	        		if(httpClient != null){
	        			httpClient.close(); //释放资源
	                }
	        	} catch (IOException e) {
	        		e.printStackTrace();
	        	}
		 }
		 return httpStr;
	}
	
	public static boolean isXml(String obj) {
		try {
			DocumentHelper.parseText(obj);
		}catch (Exception e) {
			return false;
		}
		return true;
	}
}