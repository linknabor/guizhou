package com.yumu.hexie.web.home;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.model.localservice.HomeServiceConstant;
import com.yumu.hexie.model.localservice.ServiceItem;
import com.yumu.hexie.model.localservice.bill.BaojieBill;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.o2o.BaojieService;
import com.yumu.hexie.service.o2o.HomeItemService;
import com.yumu.hexie.service.o2o.req.BaojieReq;
import com.yumu.hexie.service.user.AddressService;
import com.yumu.hexie.service.user.CouponService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.home.resp.BaojieNormalPayPage;
import com.yumu.hexie.web.home.vo.BaojieDetailVO;
import com.yumu.hexie.web.home.vo.BaojieListVO;

@Controller(value = "baojieController")
public class BaojieController extends BaseController{
    private static final Logger Log = LoggerFactory.getLogger(BaojieController.class);


    @Inject
    private BaojieService baojieService;
    @Inject
    private HomeItemService homeItemService;
    @Inject
    private AddressService addressService;
    @Inject
    private CouponService couponService;
	
	/***********************新的保洁服务*************************/
	//日常保洁类型
	@RequestMapping(value = "/serviceItem/{itemId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<ServiceItem> queryItem(@PathVariable long itemId) {
        return new BaseResult<ServiceItem>().success(homeItemService.queryById(itemId));
    }
	
	//深度保洁类型
    @RequestMapping(value = "/serviceItems/{typeId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<List<ServiceItem>> queryTypes(@PathVariable long typeId) {
        return new BaseResult<List<ServiceItem>>().success(homeItemService.queryServiceItemByType(typeId));
    }
    //日常保洁下单
    @RequestMapping(value = "/baojie/normal", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<Long> createNormalOrder(@ModelAttribute(Constants.USER)User user,@RequestBody BaojieReq req) {
        return new BaseResult<Long>().success(baojieService.create(req, user).getId());
    }
    //日常保洁类型
    @RequestMapping(value = "/baojie/normal/payinfo/{itemId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<BaojieNormalPayPage> normalPayPage(@ModelAttribute(Constants.USER)User user, @PathVariable long itemId) {
        BaojieNormalPayPage page = new BaojieNormalPayPage();
        ServiceItem item = homeItemService.queryById(itemId);
        page.setItem(item);
        page.setAddress(addressService.queryDefaultAddress(user));
        page.setCoupons(couponService.findAvaibleCoupon4ServiceType(user.getId(), HomeServiceConstant.SERVICE_TYPE_BAOJIE,item.getType(),item.getId()));
        return new BaseResult<BaojieNormalPayPage>().success(page);
    }
    //保洁支付
    @RequestMapping(value = "/baojie/pay/{billId}", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<JsSign> pay(@ModelAttribute(Constants.USER)User user,@PathVariable long billId) {
        return new BaseResult<JsSign>().success(baojieService.pay(billId,user));
    }

    //保洁确认
    @RequestMapping(value = "/baojie/confirm/{billId}", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> confirm(@ModelAttribute(Constants.USER)User user,@PathVariable long billId) {
        baojieService.confirm(billId,user);
        return new BaseResult<String>().success("订单已确认");
    }
    //保洁取消
    @RequestMapping(value = "/baojie/cancel/{billId}", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> cancel(@ModelAttribute(Constants.USER)User user,@PathVariable long billId) {
        baojieService.cancel(billId,user);
        return new BaseResult<String>().success("订单已取消");
    }

    //保洁单查询
    @RequestMapping(value = "/baojie/query/{page}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<List<BaojieListVO>> query(@ModelAttribute(Constants.USER)User user,@PathVariable int page) {
        List<BaojieListVO> vos = new ArrayList<BaojieListVO>();
        for(BaojieBill bill : baojieService.query(user, page)){
            vos.add(new BaojieListVO(bill));
        }
        return new BaseResult<List<BaojieListVO>>().success(vos);
    }

    @RequestMapping(value="baojie/notifyPayed/{orderId}",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> notifyPayed(@PathVariable long orderId,@ModelAttribute(Constants.USER)User user){
        baojieService.get(orderId, user);
        //为了作权限校验处理
        baojieService.notifyPayed(orderId);
        return new BaseResult<String>().success("通知完成");
    }

    //保洁单查询
    @RequestMapping(value = "/baojie/get/{billId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<BaojieDetailVO> query(@ModelAttribute(Constants.USER)User user,@PathVariable long billId) {
        Log.error("baojie/get/" + billId);
        return new BaseResult<BaojieDetailVO>().success(new BaojieDetailVO(baojieService.get(billId, user)));
    }
}

