package com.yumu.hexie.web.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
import com.yumu.hexie.service.shequ.ParamService;
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
    @Autowired
    private ParamService paramService;
    

    @Value(value = "${testMode}")
    private Boolean testMode;
	
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	@ResponseBody
    public BaseResult<UserInfo> userInfo(HttpSession session,@ModelAttribute(Constants.USER)User user,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User sessionUser = user;
		try {
			
			String oriApp = request.getParameter("oriApp");
			log.info("oriApp : " + oriApp);
			if (StringUtil.isEmpty(oriApp)) {
				oriApp = ConstantWeChat.APPID;
			}
			
			log.info("user in session :" + sessionUser);
			List<User> userList = userService.getByOpenId(user.getOpenid());
			if (userList!=null) {
				for (User baseduser : userList) {
					
					if (baseduser.getId() == user.getId()) {
						user = baseduser;
						break;
					}else if (StringUtils.isEmpty(baseduser.getId())&&baseduser.getOriUserId() == user.getId() ) {	//从其他公众号迁移过来的用户，登陆时session中应该是源系统的userId，所以跟原系统的比较。
						user = baseduser;
						break;
					}
				}
			}
			user = userService.getById(user.getId());
			if (user != null) {
				String userAppId = user.getAppId();	//如果根据session中信息获得的用户并非当前公众号的，比如宝房用户登陆合协公众号，则需要清空session，让他重新登陆
				if (!oriApp.equals(userAppId)) {
					user = null;
				}
			}
			if(user != null){
			    session.setAttribute(Constants.USER, user);
			    UserInfo userInfo = new UserInfo(user,operatorService.isOperator(HomeServiceConstant.SERVICE_TYPE_REPAIR,user.getId()));
			    Map<String, String> paramMap = paramService.getParamByUser(user);
			    userInfo.setCfgParam(paramMap);
			    return new BaseResult<UserInfo>().success(userInfo);
			} else {
				log.error("current user id in session is not the same with the id in database. user : " + sessionUser + ", sessionId: " + session.getId());
				session.setMaxInactiveInterval(1);//将会话过期
				Thread.sleep(2000);
				return new BaseResult<UserInfo>().success(null);
			}
		} catch (Exception e) {
			
			if (e instanceof BizValidateException) {
				throw (BizValidateException)e;
			}else {
				throw new Exception(e);
			}
		}
    }
	
	//检查当前用户是否注册，注册标准参考电话是否为有值。
	@RequestMapping(value = "/checkTell", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<?> checkTell(@ModelAttribute(Constants.USER)User user){
		if(user.getTel() != null && !user.getTel().equals("")) {
			 return BaseResult.successResult("用户已注册");
		}
		return BaseResult.fail("用户未注册");
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
    public BaseResult<UserInfo> login(HttpSession session,@PathVariable String code, @RequestBody(required = false) Map<String, String> postData) throws Exception {
		
		User userAccount = null;
		try {
	    	
			if (StringUtil.isNotEmpty(code)) {
			    if(Boolean.TRUE.equals(testMode)) {
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
				session.setAttribute(Constants.USER, userAccount);
			}
			if(userAccount == null) {
			    return new BaseResult<UserInfo>().failMsg("用户不存在！");
			}

			return new BaseResult<UserInfo>().success(new UserInfo(userAccount,
			    operatorService.isOperator(HomeServiceConstant.SERVICE_TYPE_REPAIR,userAccount.getId())));
		} catch (Exception e) {
			
			if (e instanceof BizValidateException) {
				throw (BizValidateException)e;
			}else {
				throw new Exception(e);
			}
		}
    }
	
	private void updateWeUserInfo(User userAccount, UserWeiXin newUser) {
        if(newUser != null && newUser.getSubscribe()!=null) {
            if (1 == newUser.getSubscribe()) {
                sendSubscribeCoupon(userAccount);
                userAccount.setNewRegiste(false);
            }
            userAccount.setSubscribe(newUser.getSubscribe());
            userAccount.setSubscribe_time(newUser.getSubscribe_time());
            userAccount.setShareCode(DigestUtils.md5Hex("UID["+userAccount.getId()+"]"));
            userService.save(userAccount);
        }
    }
	
	private void sendSubscribeCoupon(User user){
		
		if (!user.isNewRegiste()) {
			return ;
		}
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
	
	
	@RequestMapping(value = "/getyzm", method = RequestMethod.POST)
	@ResponseBody
    public BaseResult<String> getYzm(@RequestBody MobileYzm yzm, @ModelAttribute(Constants.USER)User user) throws Exception {
		boolean result = smsService.sendVerificationCode(user.getId(), yzm.getMobile());
		if(result) {
		    return new BaseResult<String>().failMsg("发送验证码失败");
		}
	    return  new BaseResult<String>().success("验证码发送成功");
    }
	
	@RequestMapping(value = "/getyzm1", method = RequestMethod.POST)
	@ResponseBody
    public BaseResult<String> getYzm1(@RequestBody MobileYzm yzm) throws Exception {
		boolean result = smsService.sendVerificationCode(12345, yzm.getMobile());
		if(!result) {
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
    
    @RequestMapping(value = "/cancelSubscribe", method = RequestMethod.GET)
	@ResponseBody
    public BaseResult<String> cancelSubscribe(String weixin_id){
    	List<User> userList = userService.getByOpenId(weixin_id);
    	if (userList!=null && userList.size()>0) {
    		for (User user : userList) {
    			user.setSubscribe(0);
        		userService.save(user);
			}
    		return  new BaseResult<String>().success("关注被取消"); 
		}else {
			return  new BaseResult<String>().failMsg("未找到该用户");
		}
    	 
    }
    
    @RequestMapping(value = "/saveLocation", method = RequestMethod.GET)
   	@ResponseBody
   	public BaseResult<String> saveLocation(String weixin_id,String Latitude,String Longitude){
    	List<User> userList = userService.getByOpenId(weixin_id);
    	if (userList!=null) {
			for (User user : userList) {
				user.setLatitude(Double.parseDouble(Latitude));//纬度
		   		user.setLongitude(Double.parseDouble(Longitude));//经度
				userService.save(user);
			}
			return new BaseResult<String>().success("地理位置保存成功!");
		}else {
			return new BaseResult<String>().failMsg("未找到该用户!");
		}
   		
    	 
    }
  
}
