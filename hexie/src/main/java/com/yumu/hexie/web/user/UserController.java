package com.yumu.hexie.web.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.constant.ConstantWeChat;
import com.yumu.hexie.integration.wechat.entity.user.UserWeiXin;
import com.yumu.hexie.model.localservice.HomeServiceConstant;
import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.GotongService;
import com.yumu.hexie.service.common.SmsService;
import com.yumu.hexie.service.common.SystemConfigService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.o2o.OperatorService;
import com.yumu.hexie.service.shequ.WuyeService;
import com.yumu.hexie.service.user.CouponService;
import com.yumu.hexie.service.user.PointService;
import com.yumu.hexie.service.user.UserService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.user.req.MobileYzm;
import com.yumu.hexie.web.user.req.SimpleRegisterReq;
import com.yumu.hexie.web.user.resp.UserInfo;

@Controller(value = "userController")
public class UserController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	private static final Integer lock = 0;

	@Inject
	private UserService userService;
	@Inject
	private SmsService smsService;
    @Inject
    private PointService pointService;
    @Inject
    private WuyeService wuyeService;
    @Inject
    private CouponService couponService;
    @Inject
    private OperatorService operatorService;
    
    @Inject
    private GotongService goTongService;
    
    @Inject
    private SystemConfigService systemConfigService;
    

    @Value(value = "${testMode}")
    private String testMode;
	
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	@ResponseBody
    public BaseResult<UserInfo> userInfo(HttpSession session,@ModelAttribute(Constants.USER)User user) throws Exception {
		
		log.info("sessionUser is : " + user);
		
		User sessionUser = user;	//先拷贝一个副本，因为user很可能跟数据库中的user不是同一个。
		List<User> userList = userService.getByOpenId(user.getOpenid());	//根据openid去查,可能会有多条。但只有id相同的视为真实的登陆用户
		if (userList!=null) {
			for (User baseduser : userList) {
				if (baseduser.getId() == user.getId()) {
					user = baseduser;
					break;
				}
			}
		}
		user = userService.getById(user.getId());
		
		log.info("user in database : " + user);
		
        if(user != null){
        	session.setAttribute(Constants.USER, user);
        	UserInfo userinfo = new UserInfo(user,operatorService.isOperator(HomeServiceConstant.SERVICE_TYPE_REPAIR,user.getId()));
            return new BaseResult<UserInfo>().success(userinfo);
        } else {
        	log.error("current user id in session is not the same with the id in database. user : " + sessionUser + ", sessionId: " + session.getId());
        	log.error("will expire the session on server side .");
        	session.setMaxInactiveInterval(1);
            return new BaseResult<UserInfo>().success(null);
        }
    }

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	@ResponseBody
    public BaseResult<UserInfo> profile(HttpSession session,@ModelAttribute(Constants.USER)User user,@RequestParam String nickName,@RequestParam int sex) throws Exception {
		user = userService.saveProfile(user.getId(), nickName, sex);
		if(user != null){
			session.setAttribute(Constants.USER, user);
	        return new BaseResult<UserInfo>().success(new UserInfo(user,
	            operatorService.isOperator(HomeServiceConstant.SERVICE_TYPE_REPAIR,user.getId())));
		} else {
            return new BaseResult<UserInfo>().failMsg("用户不存在！");
        }
    }
	
	@RequestMapping(value = "/login/{code}", method = RequestMethod.POST)
	@ResponseBody
    public BaseResult<UserInfo> login(HttpSession session,@PathVariable String code) throws Exception {
		
		User userAccount = null;
		if (StringUtil.isNotEmpty(code)) {
		    if("true".equals(testMode)) {
		        try{
    		        Long id = Long.valueOf(code);
    		    	userAccount = userService.getById(id);
		        }catch(Throwable t){}
		    }
		    if(userAccount == null) {
		        userAccount = userService.getOrSubscibeUserByCode(code);
		    }
			pointService.addZhima(userAccount, 5, "zm-login-"+DateUtil.dtFormat(new Date(),"yyyy-MM-dd")+userAccount.getId());
			wuyeService.userLogin(userAccount.getOpenid());
			
			/*判断用户是否关注公众号*/
			UserWeiXin u = userService.getOrSubscibeUserByOpenId(userAccount.getOpenid());
			
			updateWeUserInfo(userAccount, u);
			
			if(StringUtil.isEmpty(userAccount.getShareCode())) {
				userAccount.generateShareCode();
				userService.save(userAccount);
			}
			session.setAttribute(Constants.USER, userAccount);
		}
		if(userAccount == null) {
            return new BaseResult<UserInfo>().failMsg("用户不存在！");
		}
		
//		if (StringUtil.isEmpty(userAccount.getBindOpenId())) {
//			return new BaseResult<UserInfo>().failCode(BaseResult.NEED_MAIN_LOGIN); 
//		}

        return new BaseResult<UserInfo>().success(new UserInfo(userAccount,
            operatorService.isOperator(HomeServiceConstant.SERVICE_TYPE_REPAIR,userAccount.getId())));
    }
	
	private void updateWeUserInfo(User userAccount, UserWeiXin newUser) {
        if(newUser != null && newUser.getSubscribe()!=null) {
            if (1 == newUser.getSubscribe()) {
                sendSubscribeCoupon(userAccount);
                userAccount.setNewRegiste(false);
            }
            userAccount.setSubscribe(newUser.getSubscribe());
            userAccount.setSubscribe_time(newUser.getSubscribe_time());
            userService.save(userAccount);
        }
    }
	
	private void sendSubscribeCoupon(User user){
		
		if (!user.isNewRegiste()) {
			return ;
		}
		
		synchronized (lock) {
			
			List<Coupon>list = new ArrayList<Coupon>();
			
			String couponStr = systemConfigService.queryValueByKey("SUBSCRIBE_COUPONS");
			String[]couponArr = null;
			if (!StringUtil.isEmpty(couponStr)) {
				couponArr = couponStr.split(",");
			}
			if (couponArr!=null) {
				for (int i = 0; i < couponArr.length; i++) {
					
					try {
						Coupon coupon = couponService.addCouponFromSeed(couponArr[i], user);
						list.add(coupon);
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}
			
			if (list.size()>0) {
				goTongService.sendSubscribeMsg(user);
			}
			
		}
		
	}
	
	
	@RequestMapping(value = "/getyzm", method = RequestMethod.POST)
	@ResponseBody
    public BaseResult<String> getYzm(@RequestBody MobileYzm yzm, @ModelAttribute(Constants.USER)User user) throws Exception {
		boolean result = smsService.sendVerificationCode(user.getId(), yzm.getMobile());
		if(result) {
		    return new BaseResult<String>().failMsg("发送验证码失败");
		}
	    return  new BaseResult<String>().success("验证码发送成功");
    }


	@RequestMapping(value = "/savePersonInfo/{captcha}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<UserInfo> savePersonInfo(HttpSession session,@RequestBody User editUser,@ModelAttribute(Constants.USER)User user,
			@PathVariable String captcha) throws Exception {
		if(StringUtil.equals(editUser.getTel(),user.getTel())) {
			user.setSex(editUser.getSex());
			user.setRealName(editUser.getRealName());
			user.setName(editUser.getName());
			session.setAttribute(Constants.USER, userService.save(user));

	        return new BaseResult<UserInfo>().success(new UserInfo(user));
		} else {
			if(!smsService.checkVerificationCode(editUser.getTel(),captcha)){
				return new BaseResult<UserInfo>().failMsg("短信校验失败！");
			} else {
				user.setTel(editUser.getTel());
				user.setSex(editUser.getSex());
				user.setRealName(editUser.getRealName());
				user.setName(editUser.getName());
				session.setAttribute(Constants.USER, userService.save(user));
	            return new BaseResult<UserInfo>().success(new UserInfo(user));
			}
		}
	}

    @RequestMapping(value = "/simpleRegister", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<UserInfo> simpleRegister(HttpSession session,@ModelAttribute(Constants.USER)User user,@RequestBody SimpleRegisterReq req) throws Exception {
        if(StringUtil.isEmpty(req.getMobile()) || StringUtil.isEmpty(req.getYzm())){
            return new BaseResult<UserInfo>().failMsg("信息请填写完整！");
        }
        boolean result = smsService.checkVerificationCode(req.getMobile(), req.getYzm());
        if(!result){
            return new BaseResult<UserInfo>().failMsg("校验失败！");
        } else {
            if(StringUtil.isNotEmpty(req.getName())) {
                user.setName(req.getName());
                user.setTel(req.getMobile());
            }
            
            user.setRegisterDate(System.currentTimeMillis());
            session.setAttribute(Constants.USER, userService.save(user));
            return new BaseResult<UserInfo>().success(new UserInfo(user));
        }
    }
    
    /**
     * 绑定主公众号的openid
     * @param user
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bindWechat/{code}", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> bindMain(@ModelAttribute(Constants.USER)User user, @PathVariable String code) throws Exception {
    	
    	User currUser = userService.getById(user.getId());
    	if (currUser == null) {
    		return new BaseResult<String>().failMsg("user does not exist !");
		}
    	if (StringUtil.isEmpty(currUser.getBindOpenId())) {
    		String openId = "";
        	if (StringUtil.isNotEmpty(code)) {
        		try {
    				openId = userService.getBindOrSubscibeUserOpenIdByCode(code);
    				currUser.setBindOpenId(openId);
    	        	currUser.setBindAppId(ConstantWeChat.BIND_APPID);
    	        	userService.save(currUser);
    			} catch (Exception e) {
    				throw new BizValidateException("get bind openid failed ! ");
    			}
        	}
        	
		}
    	
    	return new BaseResult<String>().success("bind succeeded!");
    	
    }
    
}
