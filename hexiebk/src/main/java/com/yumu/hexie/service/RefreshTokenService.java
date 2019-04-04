/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service;



/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RefreshTokenService.java, v 0.1 2016年5月9日 下午8:01:52  Exp $
 */
public interface RefreshTokenService {
	
    public void refreshAccessTokenJob();
    public void refreshJsTicketJob();
    
}
