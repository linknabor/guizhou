package com.yumu.hexie.web.common;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.integration.wechat.service.FundService;
import com.yumu.hexie.integration.wechat.vo.UnionPayVO;
import com.yumu.hexie.model.payment.PaymentConstant;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.WechatCoreService;
import com.yumu.hexie.service.o2o.BaojieService;
import com.yumu.hexie.service.o2o.XiyiService;
import com.yumu.hexie.service.payment.PaymentService;
import com.yumu.hexie.service.sales.BaseOrderService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.common.req.UrlSignReq;

/**
 * 微信校验
 * @author Administrator
 *
 */
@Controller(value = "wechatController")
public class WechatController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);

    @Inject
    private WechatCoreService wechatCoreService;
    @Inject
    private BaseOrderService baseOrderService;
    @Inject
    private XiyiService xiyiService;
    @Inject
    private BaojieService baojieService;
	@Inject
	private PaymentService paymentService;
    
    @ResponseBody
    @RequestMapping(value = "/checkSignature", method = RequestMethod.GET)
    public String checkSignature(@RequestParam(value = "signature", required = false) String signature,
    		@RequestParam(value = "timestamp", required = false) String timestamp,
    		@RequestParam(value = "nonce", required = false) String nonce,
    		@RequestParam(value = "echostr", required = false) String echostr) throws Exception {
    	LOGGER.info(String.format("checkSignature(signature:%s,timestamp:%s,nonce:%s,echostr:%s)",signature,timestamp,nonce,echostr));
    	if(checkParams(signature, timestamp, nonce)){
    		LOGGER.info("checkSignature success");
    		return echostr;
    	} else {
    		LOGGER.error("checkSignature fail");
    		return "";
    	}
    }
    
    @ResponseBody
    @RequestMapping(value = "/orderNotify", method = RequestMethod.POST )
    public String orderNotify(@RequestParam String bankType,@RequestParam String merNo,@RequestParam String orderDate,@RequestParam String orderNo,
    		@RequestParam String productId,@RequestParam String respCode,@RequestParam String respDesc,@RequestParam String signature,
    		@RequestParam String transAmt,@RequestParam String transId) throws Exception {
    	UnionPayVO unionpayvo = new UnionPayVO();
    	unionpayvo.setBankType(bankType);
    	unionpayvo.setMerNo(merNo);
    	unionpayvo.setOrderDate(orderDate);
    	unionpayvo.setOrderNo(orderNo);
    	unionpayvo.setProductId(productId);
    	unionpayvo.setRespCode(respCode);
    	unionpayvo.setRespDesc(respDesc);
    	unionpayvo.setSignature(signature);
    	unionpayvo.setTransAmt(transAmt);
    	unionpayvo.setTransId(transId);
    	LOGGER.info("银联回调进入：");
    	String is = FundService.getNotify(unionpayvo);
    	if("FAIL".equals(is)) {
    		return "FAIL";
    	}
		PaymentOrder payment = paymentService.findByPaymentNo(is);//获取billID
		payment = paymentService.refreshStatus(payment);
		if(payment.getOrderType() == PaymentConstant.TYPE_MARKET_ORDER){
            baseOrderService.update4Payment(payment);
		} else if(payment.getOrderType() == PaymentConstant.TYPE_XIYI_ORDER) {
            xiyiService.update4Payment(payment);
        } else if(payment.getOrderType() == PaymentConstant.TYPE_BAOJIE_ORDER) {
            baojieService.update4Payment(payment);
        }
    	return "SUCCESS";
    }
    //用于唤起扫码
    @ResponseBody
    @RequestMapping(value = "/getPayJsSign/{prepayId}", method = RequestMethod.GET )
    public BaseResult<JsSign> getJsSign(@RequestParam String prepayId) {
    	return new BaseResult<JsSign>().success(wechatCoreService.getPrepareSign(prepayId));
    }
    
    //用于唤起扫码
    @ResponseBody
    @RequestMapping(value = "/getUrlJsSign", method = RequestMethod.POST )
    public BaseResult<JsSign> getUrlJsSign(@RequestBody UrlSignReq urlReq) {
    	JsSign s = wechatCoreService.getJsSign(urlReq.getUrl());
    	if(s != null) {
    		return new BaseResult<JsSign>().success(s);
    	} else {
    		return new BaseResult<JsSign>().failMsg("支付初始化失败，请稍后重试！");
    	}
    	
    }

    private boolean checkParams(String signature, String timestamp, String nonce){
    	if(StringUtils.isEmpty(signature)||StringUtils.isEmpty(timestamp)||StringUtils.isEmpty(nonce)){
    		return false;
    	}
    	return wechatCoreService.checkSignature(signature, timestamp, nonce);
    }

}
