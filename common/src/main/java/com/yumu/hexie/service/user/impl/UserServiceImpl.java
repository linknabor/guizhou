package com.yumu.hexie.service.user.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.constant.ConstantWeChat;
import com.yumu.hexie.integration.wechat.entity.user.UserWeiXin;
import com.yumu.hexie.integration.wuye.WuyeUtil;
import com.yumu.hexie.integration.wuye.resp.BaseResult;
import com.yumu.hexie.integration.wuye.vo.HexieUser;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.user.TempUser;
import com.yumu.hexie.model.user.TempUserRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserRepository;
import com.yumu.hexie.service.common.WechatCoreService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.user.PointService;
import com.yumu.hexie.service.user.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
    public List<User> getByOpenId(String openId){
        return userRepository.findByOpenid(openId);
    }
	@Override
	public User getOrSubscibeUserByCode(String code) {
		
		return getTpSubscibeUserByCode(code, null);
	}
	
	@Override
	public User getTpSubscibeUserByCode(String code, String oriApp) {
		UserWeiXin user = wechatCoreService.getByOAuthAccessToken(code, oriApp);
		if(user == null) {
            throw new BizValidateException("微信信息不正确");
        }
		logger.info("userWeiXin is : " + user);
		
		String openId = user.getOpenid();
		List<User> userList = userRepository.findByOpenid(openId);
		User userAccount = null;
		if (userList!=null && userList.size()> 0) {
			if (userList.size() == 1) {
				userAccount = userList.get(0);
			}else {
				userAccount = userList.get(userList.size()-1);
			}
		}
		
		if(userAccount == null) {
            userAccount = createUser(user);
            userAccount.setNewRegiste(true);
        }
		if (StringUtils.isEmpty(userAccount.getAppId())) {
			
			updateAppId(userAccount, oriApp);
			
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
	
	/**
	 * 设置更新appid
	 * @param userAccount
	 * @param oriApp
	 * @return
	 */
	private User updateAppId(User userAccount, String oriApp) {
		
		if (StringUtils.isEmpty(oriApp)) {
			userAccount.setAppId(ConstantWeChat.APPID);	//合协用户填这个
		}else {
			userAccount.setAppId(oriApp);	//其他系统用户填自己的appId
		}
		return userRepository.save(userAccount);
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
	public List<User> getBindHouseUser(int pageNum,int pageSize) {
		return userRepository.getBindHouseUser(pageNum,pageSize);
	}
	
	@Autowired
	private TempUserRepository tempUserRepository;
	
	@Override
	public List<TempUser> getTempUser() {

		return tempUserRepository.findAll();
	}
	@Override
	public List<User> getByTel(String tel) {
		return userRepository.findByTel(tel);
	}
	@Override
	public List<TempUser> getTemp() {
	
		
		return tempUserRepository.findBySectid("161223100120926240");
	}
	@Override
	public List<String> getRepeatShareCodeUser() {
		
		return userRepository.getRepeatShareCodeUser();
	}
	@Override
	public List<User> getShareCodeIsNull() {
		
		return userRepository.getShareCodeIsNull();
	}
	@Override
	public List<User> getUserByShareCode(String shareCode) {
		return userRepository.getUserByShareCode(shareCode);
	}
	
	
   
}
