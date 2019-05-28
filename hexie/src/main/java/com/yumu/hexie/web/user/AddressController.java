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
import com.yumu.hexie.model.distribution.region.AmapAddress;
import com.yumu.hexie.model.distribution.region.Region;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.Xiaoqu;
import com.yumu.hexie.service.user.AddressService;
import com.yumu.hexie.service.user.PointService;
import com.yumu.hexie.service.user.UserService;
import com.yumu.hexie.service.user.req.AddressReq;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.user.resp.RegionInfo;

@Controller(value = "addressController")
public class AddressController extends BaseController{

    @Inject
    private AddressService addressService;
    @Inject
    private UserService userService;
	@Inject
	private PointService pointService;

	@RequestMapping(value = "/saveAddressWithXiaoqu", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<Address> saveWithXiaoqu(HttpSession session,@ModelAttribute(Constants.USER)User user,@RequestBody AddressReq req) throws Exception {
        Address address = addressService.saveAddress(req, user);
        pointService.addZhima(user, 50, "zhima-address-"+user.getId()+"-"+address.getId());
        if(user.getCurrentAddrId() == 0) {
            session.setAttribute(Constants.USER, userService.getById(user.getId()));
        }
        return new BaseResult<Address>().success(address);
    }
	
	@RequestMapping(value = "/queryXiaoqus", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<List<Xiaoqu>> queryXiaoqus(){
        return new BaseResult<List<Xiaoqu>>().success(addressService.queryXiaoqu());
    }
	
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
}
