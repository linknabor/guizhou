package com.yumu.hexie.service.common.impl;

import java.text.MessageFormat;
import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.RandomStringUtils;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.eucp.CreateBlueUtil;
import com.yumu.hexie.integration.eucp.YimeiUtil;
import com.yumu.hexie.model.commonsupport.gotong.SmsHis;
import com.yumu.hexie.model.commonsupport.gotong.SmsHisRepository;
import com.yumu.hexie.service.common.SmsService;
import com.yumu.hexie.service.common.SystemConfigService;

/**
 * Created by Administrator on 2014/12/1.
 */
@Service(value = "smsService")
public class SmsServiceImpl implements SmsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);

	@Inject
	private SystemConfigService systemConfigService;
    @Inject
    private SmsHisRepository smsHisRepository;
    @Value(value = "${sms.expire.minutes}")
    private Long expireMinutes;
    @Value(value = "${testMode}")
    private String testMode;

    @Override
    public boolean sendVerificationCode(long userId,String mobilePhone) {
        SmsHis his = smsHisRepository.findByPhoneLatest(mobilePhone);
        //FIXME 需要考虑每人每天条数
        if(his!=null
                &&
                System.currentTimeMillis() - his.getSendDate().getTime() <60000l) {
            return false;
        }
        
        String sign = getMsgSignature();
        
        String code = RandomStringUtils.randomNumeric(5);
        String message = MessageFormat.format("短信验证码{0}，在30分钟内输入有效。", code);
        message = sign.concat(message);
        
        SmsHis sms = new SmsHis();
        sms.setCode(code);
        sms.setMsg(message);
        sms.setSendDate(new Date());
        sms.setPhone(mobilePhone);
        sms.setUserId(userId);
        sms = smsHisRepository.save(sms);
        
		String sendMsg = systemConfigService.queryValueByKey("SEND_MSG");        
		boolean ret = false;
        if(!"0".equals(sendMsg)){
        	if (systemConfigService.querySmsChannel()==0) {
        		ret = YimeiUtil.sendMessage(mobilePhone, message, sms.getId());//.sendBatchMessage(account, password, mobilePhone, message);
			}else {
				ret = CreateBlueUtil.sendMessage(mobilePhone, message, sms.getId());
			}
        } else {
        	ret = true;
        }
        return ret;
    }

    private String getVerificationCode(String mobilePhone) {
    	SmsHis sms = smsHisRepository.findByPhoneLatest(mobilePhone);
    	if(sms!=null && new Date().getTime() - sms.getSendDate().getTime() <= expireMinutes*60*1000) {
    		return sms.getCode();
    	}
        return null;
    }

    @Override
    public boolean checkVerificationCode(String mobilePhone, String verificationCode) {
        final String v = getVerificationCode(mobilePhone);
        return v != null && verificationCode.equalsIgnoreCase(v);
    }
    
    @Override
    public int getByPhoneAndMesssageTypeInOneMonth(String mobilePhone, int messageType, Date date){
    	
    	return smsHisRepository.findByPhoneAndMesssageTypeInOneMonth(mobilePhone, messageType, date);
    }

	@Override
	public boolean sendMsg(long userId,String mobile, String msg,long id) {
		
		return sendMsg(userId, mobile, msg, id, 0);
	}
	
	@Override
	public boolean sendMsg(long userId,String mobile, String msg,long id, int msgType) {
		boolean res = false;
		
		String sign = getMsgSignature();
		msg = sign.concat(msg);
		try{
			
			String sendMsg = systemConfigService.queryValueByKey("SEND_MSG");
			if(!"0".equals(sendMsg)){
			    if (systemConfigService.querySmsChannel()==0) {
	                res = YimeiUtil.sendMessage(mobile, msg, id);//.sendBatchMessage(account, password, mobilePhone, message);
	            }else {
	                res = CreateBlueUtil.sendMessage(mobile, msg, id);
	            }
	        } else {
	        	res = true;
	        }
			
		}catch(Exception e) {
			LOGGER.error("未配置系统参数 SMS_CHANNEL。");
		}
		if(!res){
			LOGGER.error("消息发送失败:" + mobile + " msg:" + msg);
		} else {
			SmsHis sms = new SmsHis();
	        sms.setMsg(msg);
	        sms.setSendDate(new Date());
	        sms.setPhone(mobile);
	        sms.setUserId(userId);
	        sms = smsHisRepository.save(sms);
		}
		return res;
	}
	
	private String getMsgSignature(){
		
		//是否使用自定义签名
		String use_default_sign = systemConfigService.queryValueByKey("USE_DEFINED_MSG_SIGN"); 
		
		//1：自定义签名。0或者空：供应商签东湖e家园
		if (!"1".equals(use_default_sign)) {
			return ""; 
		}
		
		String sign = systemConfigService.queryValueByKey("DEFAULT_SIGN");	//默认签名值
		sign = "【"+sign+"】";
		
		if (StringUtil.isEmpty(sign)) {
			LOGGER.error("未配置系统参数SYS_NAME，默认值：东湖e家园");
			sign = "【东湖e家园】";
		}
		return sign;
	}
	
}
