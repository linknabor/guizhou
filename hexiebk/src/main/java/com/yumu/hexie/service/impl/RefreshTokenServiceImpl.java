/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.constant.ConstantWeChat;
import com.yumu.hexie.integration.wechat.entity.AccessToken;
import com.yumu.hexie.integration.wechat.util.WeixinUtil;
import com.yumu.hexie.service.RefreshTokenService;
import com.yumu.hexie.service.SharedSysConfigService;
import com.yumu.hexie.service.common.SystemConfigService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RefreshTokenServiceImpl.java, v 0.1 2016年5月9日 下午8:03:35  Exp $
 */
@Service("refreshTokenService")
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final Logger SCHEDULE_LOG = LoggerFactory.getLogger("com.yumu.hexie.schedule");
    
    @Inject
    private SystemConfigService systemConfigService;
    
    @Inject
    private SharedSysConfigService sharedSysConfigService;
    
    @Scheduled(cron = "0 0/20 * * * ?")
    public void refreshAccessTokenJob() {
        if(!ConstantWeChat.isMainServer()){
            return;
        }
        SCHEDULE_LOG.error("--------------------refresh token[B]-------------------");
        AccessToken at = WeixinUtil.getAccessToken();
        if (at == null) {
            SCHEDULE_LOG.error("获取token失败----------------------------------------------！！！！！！！！！！！");
            return;
        }
        sharedSysConfigService.saveAccessToken(at);
        SCHEDULE_LOG.error("--------------------refresh token[E]-------------------");
    }

    @Scheduled(cron = "0 0/20 * * * ?")
    public void refreshJsTicketJob() {
        if(!ConstantWeChat.isMainServer()){
            return;
        }
        SCHEDULE_LOG.error("--------------------refresh ticket[B]-------------------");
        String jsToken = WeixinUtil.getRefreshJsTicket(systemConfigService.queryWXAToken());
        if (StringUtil.isNotEmpty(jsToken)) {
            sharedSysConfigService.saveJsToken(jsToken);
        } else {
            SCHEDULE_LOG.error("获取ticket失败----------------------------------------------！！！！！！！！！！！");
        }
        SCHEDULE_LOG.error("--------------------refresh ticket[E]-------------------");
    }

    
    	
    
}
