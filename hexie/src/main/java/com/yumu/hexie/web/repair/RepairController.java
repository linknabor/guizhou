/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.repair;

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
import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.model.localservice.repair.RepairOrder;
import com.yumu.hexie.model.localservice.repair.RepairProject;
import com.yumu.hexie.model.localservice.repair.RepairProjectRepository;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.repair.RepairService;
import com.yumu.hexie.service.repair.req.RepairCancelReq;
import com.yumu.hexie.service.repair.req.RepairComment;
import com.yumu.hexie.service.repair.req.RepairPayReq;
import com.yumu.hexie.service.repair.resp.RepairDetailItem;
import com.yumu.hexie.service.repair.resp.RepairListItem;
import com.yumu.hexie.service.user.AddressService;
import com.yumu.hexie.vo.req.RepairOrderReq;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

/**
 * <pre>
 * 维修服务
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairController.java, v 0.1 2016年1月1日 上午9:12:14  Exp $
 */
@Controller(value = "repairController")
public class RepairController extends BaseController{
    @Inject
    private RepairProjectRepository repairProjectRepository;
    @Inject
    private RepairService repairService;
    @Inject
    private AddressService addressService;
    //查询项目
    @RequestMapping(value="repair/projects/{repairType}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<List<RepairProject>> queryProjects(@PathVariable int repairType){
        return new BaseResult<List<RepairProject>>().success(repairService.queryProject(repairType));
    }
    //查询项目
    @RequestMapping(value="repair/project/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<RepairReqPageVO> queryProject(@ModelAttribute(Constants.USER)User user,@PathVariable long projectId){
        RepairReqPageVO vo = new RepairReqPageVO();
        vo.setProject(repairProjectRepository.findOne(projectId));
        List<Address> addrs = addressService.queryAddressByUser(user.getId());
        if(addrs.size()>0){
            for(Address addr : addrs) {
                if(addr.isMain()) {
                    vo.setAddress(addr);
                    break;
                }
            }
            if(vo.getAddress() == null){
                vo.setAddress(addrs.get(0));
            }
        }
        return new BaseResult<RepairReqPageVO>().success(vo);
    }
    //提交
    @RequestMapping(value="repair", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<Long> repair(@ModelAttribute(Constants.USER)User user,@RequestBody RepairOrderReq req){
        req.setRequireDate(DateUtil.parse(req.getRequireDateStr(), "yyyy-MM-dd HH:mm"));
       // req.setRequireDateStr("2019-07-19 13:00");
        if(StringUtil.isEmpty(req.getMemo().trim())){
        	return new BaseResult<Long>().failMsg("请填写报修内容!");
        }
        Long oId = repairService.repair(req, user);
        if(oId!=null){
            return new BaseResult<Long>().success(oId);
        } else {
            return new BaseResult<Long>().failMsg("维修单提交失败，请稍后再试");
        }
    }

    //确认完工
    @RequestMapping(value="repair/finish/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<String> finish(@ModelAttribute(Constants.USER)User user,@PathVariable long orderId){
        if(repairService.finish(orderId, user)){
            return new BaseResult<String>().success("维修单已确认完成");
        } else {
            return new BaseResult<String>().failMsg("维修单确认失败，请稍后再试");
        }
    }
    
    //请求支付 POST
    @RequestMapping(value="repair/pay", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<JsSign> pay(@ModelAttribute(Constants.USER)User user,@RequestBody RepairPayReq req){
        return new BaseResult<JsSign>().success(repairService.requestPay(req.getOrderId(), req.getAmount(), user));
    }

    //线下支付
    @RequestMapping(value="repair/payOffline", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> payOffline(@ModelAttribute(Constants.USER)User user,@RequestBody RepairPayReq req){
        repairService.payOffline(req.getOrderId(), req.getAmount(), user);
        return new BaseResult<String>().success("支付成功");
    }
    //支付成功
    @RequestMapping(value="repair/paySuccess/{repairOrderId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<String> paySuccess(@ModelAttribute(Constants.USER)User user,@PathVariable long repairOrderId){
        repairService.notifyPaySuccess(repairOrderId, user);
        return new BaseResult<String>().success("支付成功");
    }
    //取消维修
    @RequestMapping(value="repair/cancel", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> cancel(@ModelAttribute(Constants.USER)User user,@RequestBody RepairCancelReq req){
        repairService.cancel(req, user);
        return new BaseResult<String>().success("维修单已取消");
    }
    //删除维修单
    @RequestMapping(value="repair/delete/{repairOrderId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<String> delete(@ModelAttribute(Constants.USER)User user,@PathVariable long repairOrderId){
        repairService.deleteByUser(repairOrderId, user);
        return new BaseResult<String>().success("维修单已删除");
    }

    //评价维修
    @RequestMapping(value="repair/comment", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> comment(@ModelAttribute(Constants.USER)User user,@RequestBody RepairComment comment){
        repairService.comment(comment, user);
        return new BaseResult<String>().success("维修单评价成功");
    }
    
    //查询列表（只显示最新20条）
    @RequestMapping(value="repair/query", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<List<RepairListItem>> query(@ModelAttribute(Constants.USER)User user){
        return new BaseResult<List<RepairListItem>>().success(
            repairService.queryTop20ByUser(user));
    }

    //查询单个
    @RequestMapping(value="repair/query/{repairOrderId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<RepairDetailItem> query(@ModelAttribute(Constants.USER)User user,@PathVariable long repairOrderId){
        RepairOrder order= repairService.queryById(repairOrderId);
        if(order.getUserId() != user.getId()) {
            return new BaseResult<RepairDetailItem>().failMsg("不存在该记录"); 
        }
        return new BaseResult<RepairDetailItem>().success(RepairDetailItem.fromOrder(order));
    }

    
    //查询
    @RequestMapping(value="operator/repair/query/{status}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<List<RepairListItem>> operatorQuery(@ModelAttribute(Constants.USER)User user,@PathVariable int status){
        
        return new BaseResult<List<RepairListItem>>().success(repairService.queryTop20ByOperatorAndStatus(user, status));
    }
    //接单
    @RequestMapping(value="operator/accept/{repairOrderId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<String> accept(@ModelAttribute(Constants.USER)User user,@PathVariable long repairOrderId){
        repairService.accept(repairOrderId, user);
        return new BaseResult<String>().success("接单成功");
    }
    
    //查询
    @RequestMapping(value="operator/repair/query/repairId/{repairOrderId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<RepairDetailItem> operatorQueryById(@ModelAttribute(Constants.USER)User user,@PathVariable long repairOrderId){
        RepairOrder order= repairService.queryById(repairOrderId);
        if(order.getOperatorUserId()!=null
                &&order.getOperatorUserId()!=0
                &&order.getOperatorUserId()  != user.getId()) {
            return new BaseResult<RepairDetailItem>().failCode(9999).failMsg("该订单不存在或已被其它维修人员抢走！"); 
        }
        return new BaseResult<RepairDetailItem>().success(RepairDetailItem.fromOrder(order));
    }
    
    //操作员结束
    @RequestMapping(value="operator/repair/finish/{repairOrderId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<String> finishByOperator(@ModelAttribute(Constants.USER)User user,@PathVariable long repairOrderId){
        repairService.finishByOperator(repairOrderId, user);
        return new BaseResult<String>().success("该维修单已完成");
    }
    //删除维修单
    @RequestMapping(value="operator/repair/delete/{repairOrderId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<String> deleteByOp(@ModelAttribute(Constants.USER)User user,@PathVariable long repairOrderId){
        repairService.deleteByOperator(repairOrderId, user);
        return new BaseResult<String>().success("维修单已删除");
    }
    
    //重新指派维修单
    @RequestMapping(value="repair/reassgin/{repairOrderId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<Long> reassgin(@ModelAttribute(Constants.USER)User user,@PathVariable long repairOrderId){
        
    	Long oId = repairService.reassgin(repairOrderId, user);
        if(oId!=null){
            return new BaseResult<Long>().success(oId);
        } else {
            return new BaseResult<Long>().failMsg("重新派单失败");
        }
    }
}
