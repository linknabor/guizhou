package com.yumu.hexie.service.user;

import java.util.List;

import com.yumu.hexie.integration.wechat.entity.user.UserWeiXin;
import com.yumu.hexie.model.user.TempUser;
import com.yumu.hexie.model.user.User;


/**
 * 用户服务
 */
public interface UserService {

    public User getById(long uId);
    public List<User> getByOpenId(String openId);
    public List<User> getByTel(String tel);
	//获取用户信息
	public User getOrSubscibeUserByCode(String code);
    public UserWeiXin getOrSubscibeUserByOpenId(String openid);
	
	//从profile页面进行修改用户信息
	public User saveProfile(long userId,String nickName,int sex);
    public User save(User user);
    public User bindPhone(User user,String phone);
	
	public User queryByShareCode(String code);
	
	//获取绑定过房子的用户
	public List<User> getBindHouseUser(int pageNum,int pageSize);
	
	public List<TempUser> getTempUser();
	
	public List<TempUser> getTemp();
	
	public List<String> getRepeatShareCodeUser();
	
	public List<User> getShareCodeIsNull();
	
	public List<User> getUserByShareCode(String shareCode);
}
