package com.yumu.hexie.service.user.impl;

import java.util.List;

import javax.inject.Inject;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.amap.AmapUtil;
import com.yumu.hexie.integration.amap.req.DataCreateReq;
import com.yumu.hexie.integration.amap.resp.DataCreateResp;
import com.yumu.hexie.model.distribution.region.AmapAddress;
import com.yumu.hexie.model.distribution.region.AmapAddressRepository;
import com.yumu.hexie.model.distribution.region.Region;
import com.yumu.hexie.model.distribution.region.RegionRepository;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.AddressRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.user.AddressService;
import com.yumu.hexie.service.user.UserService;
import com.yumu.hexie.service.user.req.AddressReq;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
    
    @Inject
    private UserService userService;
    @Inject
    private AddressRepository addressRepository;
    @Inject
    private AmapAddressRepository amapAddressRepository;
    @Inject
    private RegionRepository regionRepository;
    
    @Override
    public Address addAddress(AddressReq addressReq) {
        log.error("添加地址");
        Region xiaoqu;
        Address address = addressReq.getId() == null ||  addressReq.getId()==0 
                ? new Address() : addressRepository.findOne(addressReq.getId());
        if(StringUtil.isNotEmpty(addressReq.getXiaoquName())) {
            List<Region> xiaoqus = regionRepository.findAllByParentIdAndName(addressReq.getCountyId(),addressReq.getXiaoquName());
            //FIXME 以小区名为准而非小区ID
            if(xiaoqus == null||xiaoqus.size()== 0 ){
                Region county = regionRepository.findOne(addressReq.getCountyId());
                xiaoqu = new Region(county.getId(), county.getName(), addressReq.getXiaoquName());
                xiaoqu.setLatitude(0d);
                xiaoqu.setLongitude(0d);
                xiaoqu = regionRepository.save(xiaoqu);
            } else {
                xiaoqu = xiaoqus.get(xiaoqus.size() - 1);
            }
            BeanUtils.copyProperties(addressReq, address);
            address.setXiaoquId(xiaoqu.getId());
            Region county = regionRepository.findOne(address.getCountyId());
            Region city = regionRepository.findOne(county.getParentId());
            Region province = regionRepository.findOne(city.getParentId());
            address.setCountyId(county.getId());
            address.setCounty(county.getName());
            address.setCity(city.getName());
            address.setCityId(city.getId());
            address.setProvince(province.getName());
            address.setProvinceId(province.getId());
            address = addressRepository.save(address);

            List<Address> addrs = addressRepository.findAllByUserId(address.getUserId());
            if(getDefaultAddr(addrs) == null) {
                address.setMain(true);
                User user = userService.getById(address.getUserId());
                configDefaultAddress(user, address.getId());
            }
        } else {
            throw new BizValidateException("地址所在小区信息未填写！");
        }

        return address;
    }
    
    private Address getDefaultAddr(List<Address> addrs) {
        if(addrs == null) {
            return null;
        }
        for(Address addr : addrs) {
            if(addr.isMain()) {
                return addr;
            }
        }
        return null;
    }
    @Async
    public void fillAmapInfo(Address address) {
        log.error("高德地图插入数据！AddressId:" + address.getId());
        AmapAddress amapAddr = null;
        if(address.getAmapId()!=null&&address.getAmapId()!=0){
           amapAddr = AmapUtil.dataSearchId(address.getAmapId());
        }
        if(amapAddr == null&&StringUtil.isNotEmpty(address.getXiaoquName())) {
            DataCreateReq req = new DataCreateReq(address.getXiaoquName(), null, null,
                    address.getCity()+address.getCounty()+address.getAmapDetailAddr(),
                    address.getCity(), address.getCounty(),address.getAmapDetailAddr());
            DataCreateResp resp = AmapUtil.dataManageDataCreate(req);
            if(!resp.isSuccess()){
                String errorMsg = "";
                try {
                    errorMsg = "req:"+JacksonJsonUtil.beanToJson(req) + "\n\n";
                    errorMsg = "resp:"+JacksonJsonUtil.beanToJson(resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                log.error("高德地图插入数据失败！" + errorMsg);
                return;
            }
            try {
                Thread.sleep(5000);//等待地图经纬度生成及索引创建
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            amapAddr = AmapUtil.dataSearchId(resp.get_id());
            amapAddressRepository.save(amapAddr);
        }
        
        if(amapAddr!=null && amapAddr.getLocation()!=null){
            address = addressRepository.findOne(address.getId());
            address.initAmapInfo(amapAddr);

            addressRepository.save(address);
            
            Region xiaoqu = regionRepository.findOne(address.getXiaoquId());
            if(xiaoqu != null && Math.abs(xiaoqu.getLatitude()) < 0.1) {
                xiaoqu.setLongitude(address.getLongitude());
                xiaoqu.setLatitude(address.getLatitude());
                xiaoqu.setAmapId(address.getAmapId());
                regionRepository.save(xiaoqu);
            }
            log.error("高德地图更新成功！AmapId:" + address.getAmapId());
        } else {
            log.error("高德地图更新失败！AddressId:" + address.getId());
        }
    }
    @Override
    public Address configDefaultAddress(User user, long addressId) {
        log.error("设置用户默认地址[0]" + user.getId() + "--" + user.getCurrentAddrId() + "--" + addressId);
        Address currentAddr = addressRepository.findOne(addressId);
        if(currentAddr == null) {
            return null;
        }
        List<Address> addresses = queryAddressByUser(user.getId());
        Address oldDefaultAddr = getDefaultAddr(addresses);
        if(oldDefaultAddr != null) {
            if(oldDefaultAddr.getId() == addressId) {
                currentAddr = oldDefaultAddr;
            } else {
                oldDefaultAddr.setMain(false);
                addressRepository.save(oldDefaultAddr);
            }
        }
        currentAddr.setMain(true);
        currentAddr = addressRepository.save(currentAddr);
        log.error("设置为默认地址完成:" + currentAddr.getXiaoquName());
        
        user.setCurrentAddrId(currentAddr.getId());
        user.setProvinceId(currentAddr.getProvinceId());
        user.setProvince(currentAddr.getProvince());
        user.setCity(currentAddr.getCity());
        user.setCityId(currentAddr.getCityId());
        user.setCounty(currentAddr.getCounty());
        user.setCountyId(currentAddr.getCountyId());
        user.setXiaoquId(currentAddr.getXiaoquId());
        user.setXiaoquName(currentAddr.getXiaoquName());
        user.setLatitude(currentAddr.getLatitude());
        user.setLongitude(currentAddr.getLongitude());

        log.error("设置用户默认地址[B]" + user.getId() + "--" + user.getCurrentAddrId() + "--" + currentAddr.getId());
        user = userService.save(user);
        log.error("设置用户默认地址[E]" + user.getId() + "--" + user.getCurrentAddrId() + "--" + currentAddr.getId());
        
        return currentAddr;
    }
    @Override
    public void deleteAddress(long id, long userId) {
        Address addr = addressRepository.findOne(id);
        if(addr.isMain()) {
            throw new BizValidateException("无法删除默认地址！");
        }
        if(addr.getUserId() != userId) {
            throw new BizValidateException("无法删除该地址！");
        }
        addressRepository.delete(addr);
    }
    @Override
    public List<Address> queryAddressByUser(long userId) {
        return addressRepository.findAllByUserId(userId);
    }
    @Override
    public List<Region> queryRegions(int type, long regionId) {
        if(type == 1) {
            return regionRepository.findAllByRegionType(1);
        }
        return regionRepository.findAllByRegionTypeAndParentId(type, regionId);
    }
    @Override
    public List<AmapAddress> queryAmapYuntuLocal(String city, String keyword) {
        return AmapUtil.dataSearchLocal(city, keyword);
    }
    @Override
    public DataCreateResp addAmapYuntuDataCreate(DataCreateReq newAddr) {
        return AmapUtil.dataManageDataCreate(newAddr);
    }
    @Override
    public Address queryAddressById(long id) {
        return addressRepository.findOne(id);
    }
    
    /** 
     * 根据坐标查找周围10个小区
     * @see com.yumu.hexie.service.user.AddressService#queryNearByCoordinate()
     */
    @Override
    public List<AmapAddress> queryAroundByCoordinate(double longitude, double latitude) {
        return AmapUtil.queryAroundByCoordinate(longitude, latitude);
    }
    @Override
    public Address queryDefaultAddress(User user) {
        if(user.getCurrentAddrId() > 0) {
            return queryAddressById(user.getCurrentAddrId());
        } else {
            List<Address> addrs = addressRepository.findAllByUserId(user.getId());
            if(addrs.size()>0) {
                Address addr = addrs.get(0);
                return configDefaultAddress(user, addr.getId());
            }
            return null;
        }
    }
}
