/**
 * <p>标    题: 物业平台</p>
 * <p>描    述: </p>
 * <p>版    权: Copyright (c) 2012</p>
 * <p>公    司: 上海泓智信息科技有限公司</p>
 * <p>创建时间: 2015年1月7日下午6:26:20</p>
 * @author huym
 * @version 2.0
 * WechatConfig
 */
package com.yumu.hexie.integration.wechat.util;

import java.util.HashMap;
import java.util.Map;

public class WechatConfig {

	public final static String INPUT_CHARSET = "UTF-8";

	public final static String SUCCESS = "SUCCESS";

	public final static String PAYING = "USERPAYING";

	public final static String REFUNDING = "REFUND";

	public final static String FAIL = "FAIL";

	public final static String PROCESSING = "PROCESSING";

	public final static String NOTSURE = "NOTSURE";

	public final static String CHANGE = "CHANGE";

	public static Map<String, String> errCode = null;

	public static Map<String, String> statusCode = null;

	public static Map<String, String> retCode = null;

	static {

		errCode = new HashMap<String, String>();
		statusCode = new HashMap<String, String>();
		retCode = new HashMap<String, String>();

		// 通信成功，但业务不成功的返回码
		errCode.put("SYSTEMERROR", "接口后台错误");
		errCode.put("INVALID_TRANSACTIONID", "无效 transaction_id");
		errCode.put("PARAM_ERROR", "提交参数错误");
		errCode.put("ORDERPAID", "订单已支付");
		errCode.put("OUT_TRADE_NO_USED", "商户订单号重复");
		errCode.put("NOAUTH", "商户无权限");
		errCode.put("NOTENOUGH", "余额不足");
		errCode.put("NOTSUPORTCARD", "不支持卡类型");
		errCode.put("ORDERCLOSED", "订单已关闭");
		errCode.put("BANKERROR", "银行系统异常");
		errCode.put("REFUND_FEE_INVALID", "退款金额大于支付金额");
		errCode.put("ORDERNOTEXIST", "订单不存在");

		// 通信和业务都成功后，交易的状态码
		statusCode.put("SUCCESS", "支付成功");
		statusCode.put("REFUND", "转入退款");
		statusCode.put("NOTPAY", "未支付");
		statusCode.put("CLOSED", "已关闭");
		statusCode.put("REVOKED", "已撤销");
		statusCode.put("USERPAYING", "用户支付中");
		statusCode.put("NOPAY", "未支付(输入密码或确认支付超时)");
		statusCode.put("PAYERROR", "支付失败(其他原因，如银行返回失败)");

		// 与手机端的通信接口
		retCode.put("SUCCESS", "00"); // 交易成功
		retCode.put("SYSTEMERROR", "11"); // 接口后台错误
		retCode.put("PARAM_ERROR", "12"); // 提交参数错误
		retCode.put("ORDERPAID", "13"); // 订单已支付
		retCode.put("OUT_TRADE_NO_USED", "14"); // 商户订单号重复
		retCode.put("NOAUTH", "15"); // 商户无权限
		retCode.put("NOTENOUGH", "16"); // 余额不足
		retCode.put("NOTSUPORTCARD", "17"); // 不支持卡类型
		retCode.put("ORDERCLOSED", "18"); // 订单已关闭
		retCode.put("BANKERROR", "19"); // 银行系统异常
		retCode.put("REFUND_FEE_INVALID", "20"); // 退款金额大于支付金额
		retCode.put("ORDERNOTEXIST", "21"); // 订单不存在

		retCode.put("REFUND", "31"); // 转入退款
		retCode.put("NOTPAY", "32"); // 未支付
		retCode.put("CLOSED", "33"); // 已关闭
		retCode.put("REVOKED", "34"); // 已撤销
		retCode.put("USERPAYING", "35"); // 用户支付中
		retCode.put("NOPAY", "36"); // 未支付(输入密码或确认支付超时)
		retCode.put("PAYERROR", "37"); // 支付失败(其他原因，如银行返回失败)

		retCode.put("ABNORMAL_COMM", "99"); // 通信异常

	}

}
