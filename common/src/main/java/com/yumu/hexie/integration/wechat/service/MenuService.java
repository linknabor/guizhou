package com.yumu.hexie.integration.wechat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.integration.wechat.entity.common.WechatResponse;
import com.yumu.hexie.integration.wechat.entity.menu.Button;
import com.yumu.hexie.integration.wechat.entity.menu.Menu;
import com.yumu.hexie.integration.wechat.util.WeixinUtil;

/**
 * 菜单创建
 */
public class MenuService {

	private static final Logger log = LoggerFactory.getLogger(MenuService.class);

	/**
	 * 菜单创建（POST） 限100（次/天）
	 */
	public static String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 菜单查询
	 */
	public static String MENU_GET = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	/**
	 * 菜单删除
	 */
	public static String MENU_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param jsonMenu
	 *            json格式
	 * @return 状态 0 表示成功、其他表示失败
	 */
	public static Integer createMenu(String jsonMenu, String accessToken) {
		WechatResponse jsonObject = WeixinUtil.httpsRequest(MENU_CREATE, "POST", jsonMenu, accessToken);
		if(null != jsonObject)
			return jsonObject.getErrcode();
		return 1;
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @return 0表示成功，其他值表示失败
	 */
	public static Integer createMenu(Menu menu, String accessToken) {
		try {
			return createMenu(JacksonJsonUtil.beanToJson(menu), accessToken);//JSONObject.valueToString(menu));
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}


	/**
	 * 查询菜单
	 * 
	 * @return 菜单结构json字符串
	 */
	public static WechatResponse getMenuJson(String accessToken) {
		return WeixinUtil.httpsRequest(MENU_GET, "GET", null, accessToken);
	}

	/**
	 * 删除菜单
	 * 
	 * @return 菜单结构json字符串
	 */
	public static WechatResponse deleteMenuJson(String accessToken) {
		return WeixinUtil.httpsRequest(MENU_DELETE, "GET", null, accessToken);
	}
	
	/**
	 * 查询菜单
	 * @return Menu 菜单对象
	 */
	public static Menu getMenu(String accessToken) {
		Menu menu =  getMenuJson(accessToken).getMenu();
		return menu;
	}

	public static void main(String[] args) {
//		deleteMenuJson();
//		getMenu();
		//		kelebao();
	//	bingquan();
	}
	
//	private static void bingquan() {
//	Button sb11 = new Button("我的房子", "view", null,
//			"http://www.e-shequ.com/weixin/index.php?m=PersonalInfo&a=myHouse"
//			+ "&response_type=code&scope=snsapi_userinfo&state=quick",null, null);
//	Button sb12 = new Button("缴费记录", "view", null,
//			 "http://www.e-shequ.com/weixin/index.php?m=MyBill&a=paymentRecord"
//			+ "&response_type=code&scope=snsapi_userinfo&state=quick",null, null);
//	Button btn1 = new Button("我是业主", "click", null, null, null,new Button[] {sb11,sb12});
//
//	Button sb21 = new Button("团购拼单", "view", null,
//			 "http://www.e-shequ.com/weixin/group/index.html",null, null);
//	Button sb22 = new Button("到家服务", "view", null,
//			"http://www.e-shequ.com/weixin/home/index.html",null, null);
//	Button btn2 = new Button("我爱生活", "click", null, null, null,new Button[] {sb21,sb22});
//
//	Button sb31 = new Button("扫描条形码", "scancode_waitmsg", "123",
//			null,null, null);
//	Button sb32 = new Button("我的账单", "view", null,
//			"http://www.e-shequ.com/weixin/index.php?m=MyBill&a=index"
//			+ "&response_type=code&scope=snsapi_userinfo&state=quick",null, null);
//	Button btn3 = new Button("我要缴费", "click", null, null, null,new Button[] {sb31,sb32});
//
//	Menu menu = new Menu(new Button[] {btn1, btn2, btn3});
//	createMenu(menu);
//}
	

	private static void bingquan(String accessToken) {
		Button sb11 = new Button("我的房子", "view", null,
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89c743b2fa762a2c"
				+ "&redirect_uri="
				+ "http%3A%2F%2Fwww.e-shequ.com%2F%2Fweixin%2Findex.php%3Fm%3DPersonalInfo%26a%3DmyHouse"
				+ "&response_type=code&scope=snsapi_userinfo&state=quick#wechat_redirect",null, null);
		Button sb12 = new Button("缴费记录", "view", null,
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89c743b2fa762a2c"
				+ "&redirect_uri="
				+ "http%3A%2F%2Fwww.e-shequ.com%2Fweixin%2Findex.php%3Fm%3DMyBill%26a%3DpaymentRecord"
				+ "&response_type=code&scope=snsapi_userinfo&state=quick#wechat_redirect",null, null);
		Button btn1 = new Button("我是业主", "click", null, null, null,new Button[] {sb11,sb12});

		Button sb21 = new Button("团购拼单", "view", null,
				 "http://www.e-shequ.com/weixin/group/index.html",null, null);
		Button sb22 = new Button("到家服务", "view", null,
				"http://www.e-shequ.com/weixin/home/index.html",null, null);
		Button btn2 = new Button("我爱生活", "click", null, null, null,new Button[] {sb21,sb22});

		Button sb31 = new Button("扫描条形码", "scancode_waitmsg", "123",
				null,null, null);
		Button sb32 = new Button("我的账单", "view", null,
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89c743b2fa762a2c"
				+ "&redirect_uri="
				+ "http%3A%2F%2Fwww.e-shequ.com%2Fweixin%2Findex.php%3Fm%3DMyBill%26a%3Dindex"
				+ "&response_type=code&scope=snsapi_userinfo&state=quick#wechat_redirect",null, null);
		Button btn3 = new Button("我要缴费", "click", null, null, null,new Button[] {sb31,sb32});

		Menu menu = new Menu(new Button[] {btn1, btn2, btn3});
		createMenu(menu, accessToken);
	}
	
//	private static void old() {
//		Button sb2 = new Button("快捷缴费", "view", null, "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89c743b2fa762a2c"
//				+ "&redirect_uri=http%3A%2F%2Fwww.e-shequ.com%2F%2Fweixin%2Findex.php%3Fm%3DMyBill%26a%3DquickPay&response_type=code&scope=snsapi_userinfo&state=quick#wechat_redirect",null, null);
//		Button sb3 = new Button("账单缴费", "view", null, "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89c743b2fa762a2c"
//				+ "&redirect_uri=http%3A%2F%2Fwww.e-shequ.com%2F%2Fweixin%2Findex.php%3Fm%3DMyBill%26a%3Dindex&response_type=code&scope=snsapi_userinfo&state=bill#wechat_redirect", null,null);
//		Button btn1 = new Button("我要缴费", "click", null, null, null,new Button[] {sb2,sb3});
//
//		Button sb21 = new Button("我的房子", "view", null, "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89c743b2fa762a2c"
//				+ "&redirect_uri=http%3A%2F%2Fwww.e-shequ.com%2F%2Fweixin%2Findex.php%3Fm%3DPersonalInfo%26a%3DmyHouse&response_type=code&scope=snsapi_userinfo&state=house#wechat_redirect", null,null);
//		Button sb22 = new Button("缴费记录", "view", null,  "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx89c743b2fa762a2c"
//				+ "&redirect_uri=http%3A%2F%2Fwww.e-shequ.com%2F%2Fweixin%2Findex.php%3Fm%3DMyBill%26a%3DpaymentRecord&response_type=code&scope=snsapi_userinfo&state=pay#wechat_redirect",null, null);
//		Button btn2 = new Button("我的账户", "click", null, null, null,new Button[] {sb21 ,sb22});
//		Button btn3 = new Button("扫描条形码", "scancode_waitmsg", "123", null, null,null);
//
//		Menu menu = new Menu(new Button[] {btn3, btn2, btn1});
//		createMenu(menu);
//	}
	private static void kelebao(String accessToken) {
		Button sb1 = new Button("输单号", "view", null, "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxea2a087a5eb8258b"
				+ "&redirect_uri=http://klb.shango2o.com/wechat/queryLogistic?response_type=code&scope=snsapi_base&state=1#wechat_redirect",null, null);
		Button sb2 = new Button("扫条码", "scancode_waitmsg", "rselfmenu_0_1", null, null,null);
		Button btn1 = new Button("查快递", "click", null, null, null,new Button[] {sb1,sb2 });

		
		Button btn2 = new Button("寄快递", "view", null,  "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxea2a087a5eb8258b"
				+ "&redirect_uri=http://klb.shango2o.com/wechat/delivery?response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect",null, null);

		Button sb3 = new Button("我的订单", "view", null, "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxea2a087a5eb8258b"
				+ "&redirect_uri=http://klb.shango2o.com/wechat/orderList?response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect",
				null,null);
		Button sb4 = new Button("用户评分", "view", null, "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxea2a087a5eb8258b"
				+ "&redirect_uri=http://klb.shango2o.com/wechat/pingfenform?response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect",
				null,null);
		Button sb5 = new Button("个人中心", "view", null, "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxea2a087a5eb8258b"
				+ "&redirect_uri=http://klb.shango2o.com/wechat/person?response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect"
				,null,
				null);
		Button sb6 = new Button("关于我们", "view", null, "http://mp.weixin.qq.com/s?__biz=MzAwNzI5NjExOA==&mid=205859601&idx=1"
				+ "&sn=4dc730bc41e60dc134f55b34a7f9466c&scene=18#wechat_redirect",null,null);
		//Button sb7 = new Button("联系我们", "text", null, null,"4000-558-666",null);
		
		Button btn3 = new Button("我的", "click", null, null,null, new Button[] {
				sb3,sb4,sb5,sb6 });

		Menu menu = new Menu(new Button[] { btn1, btn2, btn3 });
		createMenu(menu, accessToken);
	}
}
