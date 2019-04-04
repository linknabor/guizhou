package com.yumu.hexie.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 * 定制自己的HttpClient,确保唯一实例，提供全局访问接口
 * 自定义timeout时间等参数
 */
public class MyHttpClient {
    private static final int   TIMEOUT                = 40000;
    private static final int   TIMEOUT_SOCKET         = 60000;

	private static final Logger LOGGER = LoggerFactory.getLogger(MyHttpClient.class);
    private MyHttpClient() {
    }

    // 每次都返回新的HttpClient实例
    public static HttpClient getNewInstance() {
        HttpClient newInstance;

        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);

        // 自定义三个timeout参数

        /*
         * 1.set a timeout for the connection manager,it defines how long we
         * should wait to get a connection out of the connection pool managed by
         * the connection manager
         */
        ConnManagerParams.setTimeout(params, 5000);

        /*
         * 2.The second timeout value defines how long we should wait to make a
         * connection over the network to the server on the other end
         */
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);

        /*
         * 3.we set a socket timeout value to 4 seconds to define how long we
         * should wait to get data back for our request.
         */
        HttpConnectionParams.setSoTimeout(params, TIMEOUT_SOCKET);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);

        newInstance = new DefaultHttpClient(conMgr, params);

        return newInstance;
    }

    public static HttpResponse execute(HttpUriRequest paramHttpUriRequest)
                                                                                           throws ClientProtocolException,
                                                                                           IOException {
        HttpResponse response = getNewInstance().execute(paramHttpUriRequest);
        return response;
    }

    public static String getStringFromResponse(HttpResponse resp, String encoding)
                                                                                  throws UnsupportedEncodingException,
                                                                                  IllegalStateException,
                                                                                  IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity()
            .getContent(), encoding));

        StringBuffer response = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            response.append(line).append(System.getProperty("line.separator"));
        }
        reader.close();
        return response.toString();
    }
}
