package com.yumu.hexie.service.common;

import java.util.Date;


/**
 * Created by Administrator on 2014/12/1.
 */
public interface SmsService {
	
	public boolean sendMsg(long userId,String mobile,String msg,long id);
	
	public boolean sendMsg(long userId, String mobile, String msg, long id, int msgType);
	
    public boolean sendVerificationCode(long userId,String mobilePhone);

    public boolean checkVerificationCode(String mobilePhone, String verificationCode);

	public int getByPhoneAndMesssageTypeInOneMonth(String mobilePhone, int messageType, Date date);

}
