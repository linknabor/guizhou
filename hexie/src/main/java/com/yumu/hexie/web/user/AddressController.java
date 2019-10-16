package com.yumu.hexie.web.user;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.distribution.RgroupAreaItem;
import com.yumu.hexie.model.distribution.RgroupAreaItemRepository;
import com.yumu.hexie.model.distribution.region.AmapAddress;
import com.yumu.hexie.model.distribution.region.Region;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.user.AddressService;
import com.yumu.hexie.service.user.PointService;
import com.yumu.hexie.service.user.RegionService;
import com.yumu.hexie.service.user.UserService;
import com.yumu.hexie.service.user.req.AddressReq;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.user.resp.RegionInfo;
import com.yumu.hexie.web.user.resp.SharedVo;

@Controller(value = "addressController")
public class AddressController extends BaseController{

    @Inject
    private AddressService addressService;
    @Inject
    private UserService userService;
	@Inject
	private PointService pointService;
	@Inject
	private RegionService regionService;
	@Inject
	private RgroupAreaItemRepository rgroupAreaItemRepository;

	@RequestMapping(value = "/address/delete/{addressId}", method = RequestMethod.POST)
	@ResponseBody
    public BaseResult<String> deleteAddress(@ModelAttribute(Constants.USER)User user,@PathVariable long addressId) throws Exception {
		addressService.deleteAddress(addressId, user.getId());
        return BaseResult.successResult("删除地址成功");
    }

	@RequestMapping(value = "/address/query/{addressId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<Address> queryAddressById(@ModelAttribute(Constants.USER)User user,@PathVariable long addressId) throws Exception {
		return BaseResult.successResult(addressService.queryAddressById(addressId));
	}

	@RequestMapping(value = "/address/default/{addressId}", method = RequestMethod.POST)
	@ResponseBody
    public BaseResult<String> defaultAddress(HttpSession session,@ModelAttribute(Constants.USER)User user,@PathVariable long addressId) throws Exception {
		Address addr = addressService.configDefaultAddress(user, addressId);
        if(addr == null) {
        	BaseResult.fail("设置默认地址失败！");
        }
        session.setAttribute(Constants.USER, user);
		return BaseResult.successResult("设置默认地址成功");
    }

	@RequestMapping(value = "/addresses", method = RequestMethod.GET)
	@ResponseBody
    public BaseResult<List<Address>> queryAddressList(@ModelAttribute(Constants.USER)User user) throws Exception {
		List<Address> addresses = addressService.queryAddressByUser(user.getId());
		BaseResult<List<Address>> r = BaseResult.successResult(addresses);
		return r;
    }
	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	@ResponseBody
    public BaseResult<Address> save(HttpSession session,@ModelAttribute(Constants.USER)User user,@RequestBody AddressReq address) throws Exception {
		if(address.getCountyId() == 0){
			return new BaseResult<Address>().failMsg("请重新选择所在区域");
		}
		if(StringUtil.isEmpty(address.getXiaoquName()) || StringUtil.isEmpty(address.getDetailAddress())){
			return new BaseResult<Address>().failMsg("请重新填写小区和详细地址");
		}
		if (StringUtil.isEmpty(address.getReceiveName()) || StringUtil.isEmpty(address.getTel())) {
			return new BaseResult<Address>().failMsg("请检查真实姓名和手机号码是否正确");
		}
		address.setUserId(user.getId());
		if (StringUtil.isEmpty(address.getAmapId())) {
			address.setAmapId(0l);
		}
		Address addr = addressService.addAddress(address);
		//本方法内调用无法异步
		addressService.fillAmapInfo(addr);
		user = userService.getById(user.getId());
		pointService.addZhima(user, 50, "zhima-address-"+user.getId()+"-"+address.getId());
		session.setAttribute(Constants.USER, user);
		return new BaseResult<Address>().success(addr);
    }
    @RequestMapping(value = "/regions/{type}/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<List<Region>> queryRegions(@PathVariable int type,@PathVariable long parentId){
        List<Region> regions = addressService.queryRegions(type, parentId);
        return BaseResult.successResult(regions);
    }

    @RequestMapping(value = "/regionsv2/{type}/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<List<RegionInfo>> queryRegionsV2(@PathVariable int type,@PathVariable long parentId){
        List<Region> regions = addressService.queryRegions(type, parentId);
        List<RegionInfo> infos = new ArrayList<RegionInfo>();
        for(Region r : regions) {
            infos.add(new RegionInfo(r.getName(),r.getId()));
        }
        return new BaseResult<List<RegionInfo>>().success(infos);
    }
    
	//add by zhangxiaonan for amap
	@RequestMapping(value = "/amap/{city}/{keyword}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<AmapAddress>> queryAmapYuntuLocal(@PathVariable String city,@PathVariable String keyword){
		return BaseResult.successResult(addressService.queryAmapYuntuLocal(city, keyword));
	}
	
	@RequestMapping(value = "/amap/{longitude}/{latitude}/around/", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<AmapAddress>> queryAround(@PathVariable double longitude, @PathVariable double latitude){
		return BaseResult.successResult(addressService.queryAroundByCoordinate(longitude, latitude));
	}
	
	@RequestMapping(value = "/getRegionByRuleId/{ruleId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<SharedVo> queryAddrByShareCode(HttpSession session, @ModelAttribute(Constants.USER)User user,@PathVariable String ruleId) {
		Address address = new Address();
		if(!StringUtil.isEmpty(ruleId)){
			
			List<RgroupAreaItem> list = rgroupAreaItemRepository.findByRuleId(Long.valueOf(ruleId));
			if (list != null && list.size() > 0) {
				
				RgroupAreaItem item = list.get(0);
				
				Region sect = regionService.getRegionInfoById(item.getRegionId());
				address.setXiaoquId(sect.getId());
				address.setXiaoquName(sect.getName());
				address.setXiaoquAddress(sect.getXiaoquAddress());
				
				Region dist = regionService.getRegionInfoById(sect.getParentId());
				address.setCounty(dist.getName());
				address.setCountyId(dist.getId());
				
				Region city = regionService.getRegionInfoById(dist.getParentId());
				address.setCity(city.getName());
				address.setCityId(city.getId());
				
				Region province = regionService.getRegionInfoById(city.getParentId());
				address.setProvince(province.getName());
				address.setProvinceId(province.getId());
				
			}
			
		}
		SharedVo vo = new SharedVo();
		vo.setAddress(address);
		vo.setBuyer(user);
		return new BaseResult<SharedVo>().successResult(vo);
	}
}
