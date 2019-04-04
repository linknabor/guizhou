package com.yumu.hexie.web.home;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.model.localservice.HomeCart;
import com.yumu.hexie.model.localservice.HomeServiceConstant;
import com.yumu.hexie.model.localservice.ServiceType;
import com.yumu.hexie.model.localservice.bill.YunXiyiBill;
import com.yumu.hexie.model.redis.Keys;
import com.yumu.hexie.model.redis.RedisRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.o2o.HomeItemService;
import com.yumu.hexie.service.o2o.XiyiService;
import com.yumu.hexie.service.o2o.req.CommonBillReq;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.home.resp.XiyiDetail;
import com.yumu.hexie.web.home.resp.XiyiItemsVO;
import com.yumu.hexie.web.home.resp.XiyiListItem;

@RequestMapping("yunxiyi")
@Controller(value = "yunxiyiController")
public class YunxiyiController extends BaseController{
    @Inject
    private RedisRepository redisRepository;
    @Inject
    private XiyiService xiyiService;
    @Inject
    private HomeItemService homeItemService;
    
	@RequestMapping(value = "/serviceTypes/{region}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<ServiceType>> queryTypes(@PathVariable long region) {
		return new BaseResult<List<ServiceType>>().success(homeItemService.queryServiceTypeByRegion(HomeServiceConstant.SERVICE_TYPE_XIYI,
            region));
    }

    @RequestMapping(value = "/serviceItems/{region}/{type}", method = RequestMethod.GET)
    @ResponseBody
	public BaseResult<XiyiItemsVO> queryItems(@PathVariable long region,@PathVariable long type){
        XiyiItemsVO v = new XiyiItemsVO();
        v.setItems(homeItemService.queryServiceItemByRegion(type, region));
        v.setType(homeItemService.queryTypeById(type));
        return new BaseResult<XiyiItemsVO>().success(v);
	}
    
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<YunXiyiBill> createOrder(@RequestBody CommonBillReq req,@ModelAttribute(Constants.USER)User user){
        HomeCart cart = redisRepository.getHomeCart(Keys.uidHomeCardKey(user.getId()));
        return new BaseResult<YunXiyiBill>().success(xiyiService.createBill(user,req, cart));
    }
    
    @RequestMapping(value="/pay/{orderId}",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<JsSign> requestPay(@PathVariable long orderId,@ModelAttribute(Constants.USER)User user){
        YunXiyiBill bill = xiyiService.queryById(orderId);
        if(bill.getUserId() != user.getId()) {
            throw new BizValidateException("不能操作他人的订单");
        }
        return new BaseResult<JsSign>().success(xiyiService.pay(bill,user.getOpenid()));
    }

    @RequestMapping(value="/notifyPayed/{orderId}",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> notifyPayed(@PathVariable long orderId,@ModelAttribute(Constants.USER)User user){
        YunXiyiBill bill = xiyiService.queryById(orderId);
        if(bill.getUserId() != user.getId()) {
            throw new BizValidateException("不能操作他人的订单");
        }
        xiyiService.notifyPayed(orderId);
        return new BaseResult<String>().success("通知完成");
    }
    @RequestMapping(value="/bills/{page}",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<List<XiyiListItem>> queryBillList(@PathVariable long page,@ModelAttribute(Constants.USER)User user){
        List<XiyiListItem> res = new ArrayList<XiyiListItem>();
        for(YunXiyiBill bill : xiyiService.queryBills(user.getId(), 0)) {
            res.add(new XiyiListItem(bill));
        }
        return new BaseResult<List<XiyiListItem>>().success(res);
    }
    

    @RequestMapping(value="/bill/{billId}",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<XiyiDetail> queryBill(@PathVariable long billId,@ModelAttribute(Constants.USER)User user){
        YunXiyiBill bill =  xiyiService.queryById(billId);
        if(bill.getUserId() != user.getId()){
            throw new BizValidateException("无法查看他人订单！");
        }
        XiyiDetail d = new XiyiDetail(bill);
        d.setItems(xiyiService.findItems(billId));
        return new BaseResult<XiyiDetail>().success(d);
    }
    
    @RequestMapping(value="/bill/{billId}/cancel",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> cancel(@PathVariable long billId,@ModelAttribute(Constants.USER)User user){
        xiyiService.cancel(billId, user.getId());
        return new BaseResult<String>().success("success");
    }

    @RequestMapping(value="/bill/{billId}/signed",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> signed(@PathVariable long billId,@ModelAttribute(Constants.USER)User user){
        xiyiService.signed(billId, user.getId());
        return new BaseResult<String>().success("success");
    }
}
