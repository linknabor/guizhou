package com.yumu.hexie.service.user.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.entity.user.UserWeiXin;
import com.yumu.hexie.integration.wuye.WuyeUtil;
import com.yumu.hexie.integration.wuye.resp.BaseResult;
import com.yumu.hexie.integration.wuye.vo.HexieUser;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserRepository;
import com.yumu.hexie.service.common.WechatCoreService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.user.PointService;
import com.yumu.hexie.service.user.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Inject
	private UserRepository userRepository;

	@Inject
	private PointService pointService;

	@Inject
	private WechatCoreService wechatCoreService;
    @Override
    public User getById(long uId){
        return userRepository.findOne(uId);
    }
    public User getByOpenId(String openId){
        return userRepository.findByOpenid(openId);
    }
	@Override
	public User getOrSubscibeUserByCode(String code) {
		UserWeiXin user = wechatCoreService.getByOAuthAccessToken(code);
		if(user == null) {
            throw new BizValidateException("微信信息不正确");
        }
		String openId = user.getOpenid();
		User userAccount = userRepository.findByOpenid(openId);
		if(userAccount == null) {
            userAccount = createUser(user);
            userAccount.setNewRegiste(true);
        }
        if(StringUtil.isEmpty(userAccount.getNickname())){
            userAccount = updateUserByWechat(user, userAccount);
        }else if(user.getSubscribe()!=null&&user.getSubscribe() != userAccount.getSubscribe()) {
            userAccount = updateSubscribeInfo(user, userAccount);
        }
        //绑定物业信息
        if(StringUtil.isEmpty(userAccount.getWuyeId()) ){
            bindWithWuye(userAccount);
        }
		return userAccount;
	}
	private User createUser(UserWeiXin user) {
		User userAccount;
		userAccount = new User();
		userAccount.setOpenid(user.getOpenid());
		userAccount.setName(user.getNickname());
		userAccount.setHeadimgurl(user.getHeadimgurl());
		userAccount.setNickname(user.getNickname());
		userAccount.setSubscribe(user.getSubscribe());
		userAccount.setSex(user.getSex());
		userAccount.setCountry(user.getCountry());
		userAccount.setProvince(user.getProvince());
		userAccount.setCity(user.getCity());
		userAccount.setLanguage(user.getLanguage());
		userAccount.setSubscribe_time(user.getSubscribe_time());
		userAccount = userRepository.save(userAccount);
		return userAccount;
	}
    private User updateSubscribeInfo(UserWeiXin user, User userAccount) {
        userAccount.setSubscribe(user.getSubscribe());
        userAccount.setSubscribe_time(user.getSubscribe_time());
        return userRepository.save(userAccount);
    }
    private User updateUserByWechat(UserWeiXin user, User userAccount) {
        userAccount.setName(user.getNickname());
        userAccount.setHeadimgurl(user.getHeadimgurl());
        userAccount.setNickname(user.getNickname());
        userAccount.setSex(user.getSex());
        if(StringUtil.isEmpty(userAccount.getCountry())
        		||StringUtil.isEmpty(userAccount.getProvince())){
        	userAccount.setCountry(user.getCountry());
        	userAccount.setProvince(user.getProvince());
        	userAccount.setCity(user.getCity());
        }
        userAccount.setLanguage(user.getLanguage());
        //从网页进入时下面两个值为空
        userAccount.setSubscribe_time(user.getSubscribe_time());
        userAccount.setSubscribe(user.getSubscribe());
        return userRepository.save(userAccount);
    }
	
	private void bindWithWuye(User userAccount) {
		BaseResult<HexieUser> r = WuyeUtil.userLogin(userAccount.getOpenid());
		if(r.isSuccess()) {
			userAccount.setWuyeId(r.getData().getUser_id());
			userRepository.save(userAccount);
		}
	}

	@Override
	public User saveProfile(long userId, String nickName, int sex) {

		User user = userRepository.findOne(userId);
		user.setNickname(nickName);
		user.setSex(sex);
		return userRepository.save(user);
	}
	@Override
	public User bindPhone(User user, String phone) {
		user.setTel(phone);
		if(user.getStatus() == 0 && StringUtil.isNotEmpty(user.getTel())){
			user.setStatus(ModelConstant.USER_STATUS_BINDED);
		}
		pointService.addZhima(user, 100, "zm-binding-"+user.getId());
		return userRepository.save(user);
	}
//	@Override
//	public void syncUsersFromWechat() {
//		List<UserWeiXin> us = wechatCoreService.getUserList();
//		for(UserWeiXin u : us) {
//			getOrSubscibeUserByOpenId(u);
//		}
//	}
	@Override
	public UserWeiXin getOrSubscibeUserByOpenId(String openid) {

		UserWeiXin user = wechatCoreService.getUserInfo(openid);
		return user;
	}
    /** 
     * @param user
     * @return
     * @see com.yumu.hexie.service.user.UserService#save(com.yumu.hexie.model.user.User)
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    /** 
     * @param code
     * @return
     * @see com.yumu.hexie.service.user.UserService#queryByShareCode(java.lang.String)
     */
    @Override
    public User queryByShareCode(String code) {
        List<User> users = userRepository.findByShareCode(code);
        return users.size() > 0 ? users.get(0) : null;
    }
	@Override
	public String getBindOrSubscibeUserOpenIdByCode(String code) {
		
		String openid = wechatCoreService.getBindOpenId(code);
		if(StringUtil.isEmpty(openid)) {
            throw new BizValidateException("微信信息不正确");
        }
		return openid;
	}

}
