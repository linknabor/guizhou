package com.yumu.hexie.model;

//枚举会好一点，但是需要时间，后续可以优化
public class ModelConstant {
	public static final String SPLIT = ",";
	//消息类型
	public static final int MESSAGE_GONGGAO = 0;
	public static final int MESSAGE_ZIXUN = 1;

	//资讯子类型
	public static final int MESSAGE_SUB_TYPE_HEATH = 1;
	public static final int MESSAGE_SUB_TYPE_FUN = 2;
	public static final int MESSAGE_SUB_TYPE_CITY = 3;
	public static final int MESSAGE_SUB_TYPE_TEATCH = 4;
	public static final int MESSAGE_SUB_TYPE_LIFE = 5;
	public static final int MESSAGE_SUB_TYPE_MATERNAL = 6;
	public static final int MESSAGE_SUB_TYPE_OTHER = 0;

	//区域类型
	public static final int REGION_ALL = 0;
	public static final int REGION_PROVINCE = 1;
	public static final int REGION_CITY = 2;
	public static final int REGION_COUNTY = 3;
	public static final int REGION_XIAOQU = 4;
	
	//订单类型
	//0.团购单 1.单个订单 3.特卖  4.团购 5.到家服务（预约）
	public static final int ORDER_TYPE_GROUP = 0;
	public static final int ORDER_TYPE_GROUP_SINGLE = 1;
	public static final int ORDER_TYPE_ONSALE = 3;
	public static final int ORDER_TYPE_RGROUP = 4;
    public static final int ORDER_TYPE_YUYUE = 5;
    public static final int ORDER_TYPE_REPAIR = 6;


	//操作业务类型
	public static final int OP_TYPE_SERVICE_ORDER = 1;
	
	//订单操作类型
	public static final int ORDER_OP_CREATE = 1;
	public static final int ORDER_OP_REQPAY = 2;
	public static final int ORDER_OP_UPDATE_PAYSTATUS = 3;
	public static final int ORDER_OP_CANCEL = 4;
	public static final int ORDER_OP_ASYNC = 5;
	public static final int ORDER_OP_SEND = 6;
	public static final int ORDER_OP_SIGN = 7;
	public static final int ORDER_OP_COMMENT = 8;
	public static final int ORDER_OP_REFUND_REQ = 9;
	public static final int ORDER_OP_RETURN = 10;
	public static final int ORDER_OP_REFUND_FINISH = 11;
	public static final int ORDER_OP_CONFIRM = 12;//变为配货中
	//订单状态
	//0. 创建完成 1. 已支付 2. 已用户取消 3. 待退款 4. 退款中  5. 已发货 6.已签收 7. 已后台取消 8. 商户取消 9. 已确认 10.已退货（退货中走线下流程） 11.已退款 12.配货中（商户确认中）
	public static final int ORDER_STATUS_INIT = 0; 
	public static final int ORDER_STATUS_PAYED = 1; 
	public static final int ORDER_STATUS_CANCEL = 2; 
	public static final int ORDER_STATUS_APPLYREFUND = 3; //暂时不使用
	public static final int ORDER_STATUS_REFUNDING = 4; 
	public static final int ORDER_STATUS_SENDED = 5; 
	public static final int ORDER_STATUS_RECEIVED = 6; 
	public static final int ORDER_STATUS_CANCEL_BACKEND = 7; 
	public static final int ORDER_STATUS_CANCEL_MERCHANT = 8;  //暂时不使用
	public static final int ORDER_STATUS_CONFIRM = 9;
	public static final int ORDER_STATUS_RETURNED = 10;
	public static final int ORDER_STATUS_REFUNDED = 11;
	
	public static final int ORDER_ASYNC_STATUS_N = 0;//未同步
	public static final int ORDER_ASYNC_STATUS_Y = 1;//已同步
	
	public static final int ORDER_PINGJIA_TYPE_N = 0;
	public static final int ORDER_PINGJIA_TYPE_Y = 1;
	
	public static final int REFUND_REASON_GROUP_CANCEL = 1;
	public static final int REFUND_REASON_GROUP_BACKEND = 2;
	
	//0商户派送 1用户自提 2第三方配送 3上门服务 4用户到店
	public static final int LOGISTIC_TYPE_MERCHANT = 0;
	public static final int LOGISTIC_TYPE_USER = 1;
	public static final int LOGISTIC_TYPE_THIRDPART = 2;
	public static final int LOGISTIC_TYPE_HOME_SER = 3;
	public static final int LOGISTIC_TYPE_GO_MERCHANT = 4;
	
	
	//拼单状态 拼单中，已成团，已取消,超时退出，订单取消
	public static final int GROUP_STAUS_GROUPING = 1;
	public static final int GROUP_STAUS_FINISH = 2;
	public static final int GROUP_STAUS_CANCEL = 3;
	public static final int GROUP_STAUS_FULL = 4;//已成团未全支付
	public static final int GROUP_STAUS_INIT = 5;//发起人创建拼单，但未支付
	
	
	//团购状态
	public static final int RGROUP_STAUS_GROUPING = 1;
	public static final int RGROUP_STAUS_FINISH = 2;
	public static final int RGROUP_STAUS_CANCEL = 3;
	//用户状态
	//0.初始化  1.绑定手机 2.设定小区 3.绑定房产 4.禁止
	public static final int USER_STATUS_INIT = 0;
	public static final int USER_STATUS_BINDED = 1;
	public static final int USER_STATUS_ADDRESSED = 2;
	public static final int USER_STATUS_HOUSED = 3;
	public static final int USER_STATUS_FOBBID = 4;
	
	//删除状态
	public static final int VALIDATE_STATUS_ONLINE = 0;
	public static final int VALIDATE_STATUS_OFFLINE = 1;
	
	
	
	//商品状态  //0.初始化   1. 上架   2.下架  3.删除
	public static final int PRODUCT_INIT = 0;
	public static final int PRODUCT_ONSALE = 1;
	public static final int PRODUCT_OFF = 2;
	public static final int PRODUCT_DELETED = 3;

	//商品类型（7~N用于特卖品牌的分类，添加注意是否冲突）
	public static final int PRODUCT_TYPE_TUTECHAN= 1;
	public static final int PRODUCT_TYPE_MEISHIDIAN= 2;
	public static final int PRODUCT_TYPE_SHUIGUOTAN= 3;
	public static final int PRODUCT_TYPE_SHENGXIANGUAN= 4;
	public static final int PRODCUT_TYPE_LINGSHIPU=5;
	public static final int PRODUCT_TYPE_ACTIVITY = 6;//用于特卖首页中活动
	public static final int PRODUCT_FEATURED= 100;//仅用于查询
	//团购分类
	public static final int RGROUP_PRODUCT_TYPE_BEYOND = 1; //自营团
	public static final int RGROUP_PRODUCT_TYPE_ZAIZAIMA = 2; //仔仔团
	public static final int RGROUP_PRDOUCT_TYPE_XIAOQIANSHANZHEN = 3; //小千山珍
	public static final int RGROUP_PRODUCT_TYPE_ABTUAN = 4; //AB团
	public static final int RGROUP_PRODUCT_TYPE_GANCHANGXIAN = 5; //敢试鲜
	public static final int RGROUP_PRODUCT_TYPE_FEATURED = 100;//精选，仅限于查询
	
	public static final int RULE_TYPE_CITY = 0;
	public static final int RULE_TYPE_NEARBY = 1;

	public static final int RULE_STATUS_ON = 1;
	public static final int RULE_STATUS_OFF = 0;
	
	public static final int DISTRIBUTION_STATUS_ON = 0;
	public static final int DISTRIBUTION_STATUS_OFF = 1;
	
	public static final int POINT_TYPE_ZIMA = 0;
	public static final int POINT_TYPE_LVDOU = 1;
	


	//帖子状态 0.正常 1.撤销
	public static final String THREAD_STATUS_NORMAL = "0";
	public static final String THREAD_STATUS_DELETED = "1";
	
	//发布信息分类
	public static final String THREAD_CATEGORY_OUTDOORS = "1";	//户外活动
	public static final String THREAD_CATEGORY_PETS = "2";	//宠物宝贝
	public static final String THREAD_CATEGORY_CATE = "3";	//吃货天地	
	public static final String THREAD_CATEGORY_STORE = "4";	//二手市场
	public static final String THREAD_CATEGORY_EDUCATION = "5";	//亲子教育
	public static final String THREAD_CATEGORY_SPORTS = "6";	//运动达人
	public static final String THREAD_CATEGORY_CHAT = "7";	//社区杂谈
	public static final String THREAD_CATEGORY_BEAUTIES = "8";	//都市丽人
			
	//帖子回复状态 0.正常 1.撤销
	public static final String COMMENT_STATUS_NORMAL = "0";
	public static final String COMMENT_STATUS_DELETED = "1";

	//预约单状态 0初始化、 1预约成功、2预约失败、3预约超时 、4需要与商户同步、 5已取消
	public static final int ORDER_STAUS_YUYUE_INIT = 0;
	public static final int ORDER_STAUS_YUYUE_SUCCESS = 1;
	public static final int ORDER_STAUS_YUYUE_FAIL = 2;
	public static final int ORDER_STAUS_YUYUE_TIMEOUT = 3;
	public static final int ORDER_STAUS_YUYUE_USED = 4;//需要与商户同步
	public static final int ORDER_STAUS_YUYUE_CANCEL = 5;//已取消

	//服务单支付方式
	public static final int YUYUE_PAYMENT_TYPE_OFFLINE = 0;//线下付款
	public static final int YUYUE_PAYMENT_TYPE_WEIXIN = 1;//微信支付

	//增加预约单的支付状态
	public static final int YUYUE_PAYSTATUS_INIT = 0;
	public static final int YUYUE_PAYSTATUS_PAYED = 1;
	public static final int YUYUE_PAYSTATUS_UNPAYED = 2;
	public static final int YUYUE_PAYSTATUS_REFUNDING = 3;
	public static final int YUYUE_PAYSTATUS_REFUNDED = 4;
	
	//增加预约单类型
	public static final int YUYUE_PRODUCT_TYPE_FASUPER = 1;//尚匠
	public static final int YUYUE_PRODUCT_TYPE_FLOWERPLUS = 2;//花加
	public static final int YUYUE_PRODUCT_TYPE_HUYAORAL = 3;//沪雅口腔
	public static final int YUYUE_PRODUCT_TYPE_DAOJIAMEI = 4;//白富美
	public static final int YUYUE_PRODUCT_TYPE_AYILAILE = 5;//阿姨来了
	public static final int YUYUE_PRODUCT_TYPE_WEIZHUANGWANG=6;//微装网
	public static final int YUYUE_PRODUCT_TYPE_BOVO=7;//邦天乐
	public static final int YUYUE_PRODUCT_TYPE_GAOFEI=8;//高飞
	public static final int YUYUE_PRODUCT_TYPE_HAORENSHENG=9;//好人生
	public static final int YUYUE_PRODUCT_TYPE_JIUYE = 10;//九曳
	public static final int YUYUE_PRODUCT_TYPE_BAOJIE = 11;//保洁服务
	public static final int YUYUE_PRODUCT_TYPE_AIXIANGBAN = 12;//爱相伴服务
	//增加服务类型
	public static final int YUYUE_SERVICE_TYPE_CAR = 0;//汽车
	public static final int YUYUE_SERVICE_TYPE_FLOWER = 1;//鲜花
	public static final int YUYUE_SERVICE_TYPE_HOUSEKEEPING = 2;//家政
	public static final int YUYUE_SERVICE_TYPE_LIFE = 3;//生活
	public static final int YUYUE_SERVICE_TYPE_DECORATION = 4;//装修
	public static final int YUYUE_SERVICE_TYPE_HEALTH = 5;//健康
	public static final int YUYUE_SERVICE_TYPE_CHESHI = 6;//车饰
	public static final int YUYUE_SERVICE_TYPE_HAORENSHENG = 7;///好人生
	//是否是精选服务
	public static final int YUYUE_SERVICE_UNHANDPICK = 0;//非精选
	public static final int YUYUE_SERVICE_HANDPICK = 1;//精选
	
	//是否是精选服务
	public static final int YUYUE_SERVICE_STATUS_UNUSED = 0;//未服务
	public static final int YUYUE_SERVICE_STATUS_USED = 1;//已服务
	
	//服务类型
	public static final int YUYUE_SERVICE_CYCLE = 0;//周期
	public static final int YUYUE_SERVICE_SINGLE = 1;//单次
	//增加banner类型
	public static final int BANNER_TYPE_SERVICE = 0;//到家
	public static final int BANNER_TYPE_ONSALE = 1;//特卖
	public static final int BANNER_TYPE_RGROUP = 2;//团购
	public static final int BANNER_TYPE_WUYE = 3;//特卖
	public static final int BANNER_TYPE_ACTIVITY = 4;//特卖首页活动
	public static final int BANNER_TYPE_ONSALEBRAND = 5;//特卖品牌
	
	public static final int BANNER_STATUS_VALID = 1;
	public static final int BANNER_STATUS_INVALID = 0;
	
	public static final int EXCEPTION_BIZ_TYPE_ORDER = 1;
	public static final int EXCEPTION_BIZ_TYPE_DAOJIA = 2;
	public static final int EXCEPTION_BIZ_TYPE_LUNTAN = 3;
	public static final int EXCEPTION_BIZ_TYPE_RGROUP = 4;
	public static final int EXCEPTION_BIZ_TYPE_PINDAN = 5;
	public static final int EXCEPTION_BIZ_TYPE_ONSALE = 6;
	public static final int EXCEPTION_BIZ_TYPE_SCHEDULE = 7;
	public static final int EXCEPTION_BIZ_TYPE_COUPON = 8;
	public static final int EXCEPTION_BIZ_TYPE_SYSTEM = 9;
	
	public static final int EXCEPTION_LEVEL_INFO = 1;
	public static final int EXCEPTION_LEVEL_ERROR = 2;
	
	

	public static final int SCHEDULE_TYPE_PAY_TIMEOUT = 1;
	public static final int SCHEDULE_TYPE_REFUND_STATUS = 2;
	public static final int SCHEDULE_TYPE_PAY_STATUS = 3;
	public static final int SCHEDULE_TYPE_PINDAN_TIMEOUT = 4;
    public static final int SCHEDULE_TYPE_TUANGOU_TIMEOUT = 5;
    public static final int SCHEDULE_TYPE_XIYI_TIMEOUT = 6;
    public static final int SCHEDULE_TYPE_BAOJIE_TIMEOUT = 7;
	

	
	//通知类型
	public static final int NOTICE_TYPE_ORDER = 1;//订单通知
	public static final int NOTICE_TYPE_COMMENT = 2;//评论消息
	public static final int NOTICE_TYPE_RGROUP = 3;//团购消息
	public static final int NOTICE_TYPE_SYS_PUSH = 4;//推送消息
	public static final int NOTICE_TYPE_YUYUE = 5;//预约消息
	
	public static final int NOTICE_SUB_TYPE_ORDERSUCCESS = 1;
	public static final int NOTICE_SUB_TYPE_ORDERSENDGOODS = 2;
	public static final int NOTICE_SUB_TYPE_ORDERREFUND = 3;
	public static final int NOTICE_SUB_TYPE_GROUPSUCCESS = 1;
	public static final int NOTICE_SUB_TYPE_GROUPFAIL = 2;
	public static final int NOTICE_SUB_TYPE_GROUPNOTIFY = 3;
	
	
	/***********现金券***************/
	//种子类型
	public static final int COUPON_SEED_ORDER_BUY = 1;
	public static final int COUPON_SEED_USER_REGIST = 2;
	public static final int COUPON_SEED_USER_SUBSCRIB = 3;
	public static final int COUPON_SEED_ACTIVITY = 4;
	public static final int COUPON_SEED_ORDER_BUY_TEMPLATE = 5;
	
	//种子状态
	public static final int COUPON_SEED_STATUS_AVAILABLE = 0;
	public static final int COUPON_SEED_STATUS_INVALID = 1;
	public static final int COUPON_SEED_STATUS_EMPTY = 2;
	public static final int COUPON_SEED_STATUS_TIMEOUT = 3;
	
	//现金券规则
	public static final int COUPON_RULE_STATUS_AVAILABLE = 1;
	public static final int COUPON_RULE_STATUS_INVALID = 2;
	
	public static final int COUPON_STATUS_AVAILABLE = 1;
	public static final int COUPON_STATUS_USED = 2;
	public static final int COUPON_STATUS_TIMEOUT = 3;
	public static final int COUPON_STATUS_LOCKED = 4;

	public static final int COLLOCATION_STATUS_AVAILABLE = 1;
	public static final int COLLOCATION_STATUS_INVAILID = 2;
}		
