/**
 * 
 */
package com.yumu.hexie.web.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;

/**
 * 重写HttpServletRequestWrapper方法，将inputstream流先存下来，以变截取其中的requestBody对象。
 * 
 * @author david
 *
 */
public class ServplatRequestWrapper extends HttpServletRequestWrapper {

	private static Logger logger = LoggerFactory.getLogger(ServplatRequestWrapper.class);

	private String body; // 请求中的对象本体
	
	/**
	 * 构造
	 * 
	 * @param request
	 */
	public ServplatRequestWrapper(HttpServletRequest request) {

		super(request);
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex.getCause());
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					logger.error(ex.getMessage(), ex.getCause());
				}
			}
		}
		body = stringBuilder.toString();
	}

	/**
	 * 重写getInputStream方法
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {
		
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
		ServletInputStream servletInputStream = new ServletInputStream() {
			public boolean isFinished() {
				return false;
			}

			public boolean isReady() {
				return false;
			}

			public void setReadListener(ReadListener readListener) {
			}

			public int read() throws IOException {
				return byteArrayInputStream.read();
			}
		};
		return servletInputStream;
	}
	
	/**
	 * 重写getReader
	 */
	@Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
