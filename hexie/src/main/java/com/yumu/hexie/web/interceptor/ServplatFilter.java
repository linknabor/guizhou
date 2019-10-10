/**
 * 
 */
package com.yumu.hexie.web.interceptor;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.web.wrapper.ServplatRequestWrapper;

/**
 * @author huym
 *
 */
@WebFilter(urlPatterns = "/servplat/*", filterName = "servplatFilter")
public class ServplatFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(ServplatFilter.class);
	
	private final static String MESSAGE_URL = "/servplat/message";
	private final static String THREAD_URL = "/servplat/thread";
	private final static String REPAIR_URL = "/servplat/repair";
	private final static String REPAIR_AREA_URL = "/servplat/repairArea";

	/**
	 * 
	 */
	public ServplatFilter() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest) {
        	HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        	String requestUri = httpServletRequest.getRequestURI();
    		if (requestUri.indexOf(MESSAGE_URL) != -1 || requestUri.indexOf(THREAD_URL) != -1 || requestUri.indexOf(REPAIR_URL) != -1
    				||requestUri.indexOf(REPAIR_AREA_URL) != -1) {
    			//TODO validate signature
    			logger.error("requestUri is : " + requestUri + ", charset encoding : " + httpServletRequest.getCharacterEncoding());
    			String csn = Charset.defaultCharset().name();
    			logger.error("default charset name is : " + csn);
    			requestWrapper = new ServplatRequestWrapper(httpServletRequest);
    		}
            
        }
        //获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
        // 在chain.doFiler方法中传递新的request对象
        if(requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
