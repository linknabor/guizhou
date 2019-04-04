/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.sales;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.AyiServiceOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.AyiServiceOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.BovoOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.BovoOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.DaoJiaMeiOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.DaoJiaMeiOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.FasuperOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.FasuperOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.FlowerPlusOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.FlowerPlusOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.GaofeiOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.GaofeiOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.HuyaOralOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.HuyaOralOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.JiuyeOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.JiuyeOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.WeiZhuangWangOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.WeiZhuangWangOrderRepository;
import com.yumu.hexie.model.tohome.AixiangbanOrder;
import com.yumu.hexie.model.tohome.AixiangbanOrderRepository;
import com.yumu.hexie.model.tohome.BaojieOrder;
import com.yumu.hexie.model.tohome.BaojieOrderRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: YuyueController.java, v 0.1 2016年4月9日 下午6:31:49  Exp $
 */
@Deprecated//准备弃用该方法，后续实现本地服务使用其它类
@Controller(value = "yuyueController")
public class YuyueController extends BaseController{
    

    @Inject
    private AyiServiceOrderRepository ayiServiceOrderRepository;
    @Inject
    private FasuperOrderRepository fasuperOrderRepository;
    @Inject
    private FlowerPlusOrderRepository flowerPlusOrderRepository;
    @Inject
    private HuyaOralOrderRepository huyaOralOrderRepository;
    @Inject
    private DaoJiaMeiOrderRepository daoJiaMeiOrderRepository;
    @Inject
    private WeiZhuangWangOrderRepository weiZhuangWangOrderRepository;
    @Inject
    private BovoOrderRepository bovoOrderRepository;
    @Inject
    private GaofeiOrderRepository gaofeiOrderRepository;
    @Inject
    private JiuyeOrderRepository jiuyeOrderRepository;
    @Inject
    private BaojieOrderRepository baojieOrderRepository;
    @Inject
    private AixiangbanOrderRepository aixiangbanOrderRepository;

    @Inject
    private YuyueOrderRepository yuyueOrderRepository;
    
    @RequestMapping(value = "/yuyueOrder/setServiceOrderId/{yuyueOrderId}/{serviceOrderId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<String> setServiceOrderIdToYuyueOrder(@PathVariable long serviceOrderId,@PathVariable long yuyueOrderId,@ModelAttribute(Constants.USER)User user) throws Exception {
        YuyueOrder order = yuyueOrderRepository.findOne(yuyueOrderId);
        if(user.getId() != order.getUserId()) {
            return new BaseResult<String>().failMsg("无法操作他人订单");
        }
        if(order.getServiceOrderId()!=0){
            return  new BaseResult<String>().failMsg("该订单已经提交，请重新下单");
        }
        order.setServiceOrderId(serviceOrderId);
        yuyueOrderRepository.save(order);
        return new BaseResult<String>().success("设置成功");
    }
    @RequestMapping(value = "/yuyueOrder/checkYuyueOrder/{yuyueOrderId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<YuyueOrder> checkYuyueOrder(@PathVariable long yuyueOrderId,@ModelAttribute(Constants.USER)User user) throws Exception {
        YuyueOrder order = yuyueOrderRepository.findOne(yuyueOrderId);
        return new BaseResult<YuyueOrder>().success(order);
    }

    @RequestMapping(value = "yuyueOrders", method = RequestMethod.GET )
    @ResponseBody
    public BaseResult<List<YuyueOrder>> yuyueOrders (@ModelAttribute(Constants.USER)User user) throws Exception {
         return new BaseResult<List<YuyueOrder>>().success(yuyueOrderRepository.findAllByUserId(user.getId(),new Sort(Direction.DESC, "createDate")));
    }

    @RequestMapping(value = "yuyueOrders/{orderId}", method = RequestMethod.GET )
    @ResponseBody
    public BaseResult<YuyueOrder> yuyueOrdersById (@ModelAttribute(Constants.USER)User user, @PathVariable long orderId) throws Exception {

         YuyueOrder order = yuyueOrderRepository.findOne(orderId);
        if(order.getUserId() != user.getId()){
            return new BaseResult<YuyueOrder>().failMsg("你没有权限查看该预约单！");
        }
         return new BaseResult<YuyueOrder>().success(order);
    }
    @RequestMapping(value = "yuyueOrders/{productType}/{orderId}", method = RequestMethod.GET )
    @ResponseBody
    public BaseResult<List<BaseModel>> yuyueOrdersAddInfo (@ModelAttribute(Constants.USER)User user, @PathVariable long orderId, @PathVariable int productType) throws Exception {
        List<BaseModel> lists = new ArrayList<BaseModel>();

        if(productType == ModelConstant.YUYUE_PRODUCT_TYPE_AYILAILE){ //阿姨来了
            AyiServiceOrder ayiServiceOrder = ayiServiceOrderRepository.findOne(orderId);
            lists.add(ayiServiceOrder);
        }else if(productType == ModelConstant.YUYUE_PRODUCT_TYPE_FASUPER){ //尚匠汽车
            FasuperOrder fasuperOrder = fasuperOrderRepository.findByYOrderId(orderId);
            lists.add(fasuperOrder);
        }else if(productType == ModelConstant.YUYUE_PRODUCT_TYPE_FLOWERPLUS){ //flowerPlus
            List<FlowerPlusOrder> flowerPlusOrders = flowerPlusOrderRepository.findByYOrderId(orderId);
            for(FlowerPlusOrder flowerPlusOrder:flowerPlusOrders){
                lists.add(flowerPlusOrder);
            }
        }else if(productType == ModelConstant.YUYUE_PRODUCT_TYPE_HUYAORAL){ //上海沪雅口腔
            HuyaOralOrder huyaOralOrder = huyaOralOrderRepository.findByYOrderId(orderId);
            lists.add(huyaOralOrder);
        }else if(productType == ModelConstant.YUYUE_PRODUCT_TYPE_DAOJIAMEI){ //白富美
            DaoJiaMeiOrder daoJiaMeiOrder = daoJiaMeiOrderRepository.findByYOrderId(orderId);
            lists.add(daoJiaMeiOrder);
        }else if(productType == ModelConstant.YUYUE_PRODUCT_TYPE_WEIZHUANGWANG){ //微装网
            WeiZhuangWangOrder weiZhuangWangOrder = weiZhuangWangOrderRepository.findByYOrderId(orderId);
            lists.add(weiZhuangWangOrder);
        }else if(productType == ModelConstant.YUYUE_PRODUCT_TYPE_BOVO){ //邦天乐
            BovoOrder bovoOrder = bovoOrderRepository.findByYOrderId(orderId);
            lists.add(bovoOrder);
        }else if(productType == ModelConstant.YUYUE_PRODUCT_TYPE_GAOFEI){ //高飞体育
            GaofeiOrder gaofeiOrder = gaofeiOrderRepository.findByYOrderId(orderId);
            lists.add(gaofeiOrder);
        }
        else if(productType == ModelConstant.YUYUE_PRODUCT_TYPE_JIUYE){ //九曳
            List<JiuyeOrder> orders = jiuyeOrderRepository.findByYOrderId(orderId);
            for(JiuyeOrder order:orders){
                lists.add(order);
            }
        }
        else if(productType == ModelConstant.YUYUE_PRODUCT_TYPE_BAOJIE){
        	BaojieOrder order = baojieOrderRepository.findByYOrderId(orderId);
    		lists.add(order);
        }
        else if(productType == ModelConstant.YUYUE_PRODUCT_TYPE_AIXIANGBAN){
        	AixiangbanOrder order = aixiangbanOrderRepository.findByYOrderId(orderId);
        	lists.add(order);
        }
        else{
            return new BaseResult<List<BaseModel>>().failMsg("服务类型错误");
        }
        return new BaseResult<List<BaseModel>>().success(lists);
    }
  
}
