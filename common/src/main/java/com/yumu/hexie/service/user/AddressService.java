package com.yumu.hexie.service.user;

import java.util.List;

import com.yumu.hexie.integration.amap.req.DataCreateReq;
import com.yumu.hexie.integration.amap.resp.DataCreateResp;
import com.yumu.hexie.model.distribution.region.AmapAddress;
import com.yumu.hexie.model.distribution.region.Region;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.user.req.AddressReq;


/**
 * 用户服务
 */
public interface AddressService {

	//添加地址
	public Address addAddress(AddressReq address);
	
	public void deleteAddress(long id,long userId);
    //设置默认地址
    public Address configDefaultAddress(User user, long addressId);

    public Address queryDefaultAddress(User user);
    //根据id查询地址
    public Address queryAddressById(long id);
	
	public List<Address> queryAddressByUser(long userId);
	
	
	public List<Region> queryRegions(int type,long regionId);
	
	public void fillAmapInfo(Address address);
	public List<AmapAddress> queryAmapYuntuLocal(String city, String keyword) ;
	public DataCreateResp addAmapYuntuDataCreate(DataCreateReq newAddr);
	
	public List<AmapAddress> queryAroundByCoordinate(double longitude, double latitude);
	
}
