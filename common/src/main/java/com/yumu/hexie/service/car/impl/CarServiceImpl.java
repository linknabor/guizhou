package com.yumu.hexie.service.car.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.car.CarBrand;
import com.yumu.hexie.model.car.CarBrandRepository;
import com.yumu.hexie.model.car.CarStyle;
import com.yumu.hexie.model.car.CarStyleRepository;
import com.yumu.hexie.model.car.CarYear;
import com.yumu.hexie.model.car.CarYearRepository;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.car.OrderCarInfo;
import com.yumu.hexie.model.market.car.OrderCarInfoRepository;
import com.yumu.hexie.model.market.saleplan.OnSaleRule;
import com.yumu.hexie.model.market.saleplan.OnSaleRuleRepository;
import com.yumu.hexie.model.redis.RedisRepository;
import com.yumu.hexie.service.car.CarService;

@Service("carService")
public class CarServiceImpl implements CarService {
	
	@Inject
	private CarBrandRepository carBrandRepository;
	
	@Inject
	private CarStyleRepository carStyleRepository;
	
	@Inject
	private CarYearRepository carYearRepository;
	
	@Inject
	private OrderCarInfoRepository orderCarInfoRepository;

	@Inject
    private RedisRepository redisRepository;
	
	@Inject
	private OnSaleRuleRepository onSaleRuleRepository;
	
	@Override
	public List<CarBrand> getCarBrandList() {
		return carBrandRepository.findAll();
	}

	@Override
	public List<CarStyle> getCarStyleListByBrandId(String brandId) {
		return carStyleRepository.findByBrandId(brandId);
	}

	@Override
	public List<CarYear> getCarYearListByBrandIdAndStyleId(String brandId,String styleId) {
		return carYearRepository.findByBrandIdAndStyleId(brandId,styleId);
	}

	@Override
	public OrderCarInfo getLastCarInfoByUserId(long userId) {
		OrderCarInfo orderCarInfo = redisRepository.getOrderCarInfo(userId);
		return orderCarInfo;
	}

	@Override
	public OrderCarInfo saveTempOrderCarInfo(OrderCarInfo carInfo) {
		String brandId = carInfo.getBrandId();
		String styleId = carInfo.getStyleId();
		String yearId = carInfo.getYearId();
		CarYear carYear = carYearRepository.findByBrandIdAndStyleIdAndYearId(brandId,styleId,yearId);
		carInfo.setBrandId(carYear.getBrandId());
		carInfo.setStyleId(carYear.getStyleId());
		carInfo.setYear(carYear.getYear());
		carInfo.setYearName(carYear.getYearName());
		redisRepository.setOrderCarInfo(carInfo);
		return carInfo;
	}

	@Override
	public void saveOrderCarInfo(ServiceOrder serviceOrder) {
		if(serviceOrder.getOrderType()==ModelConstant.ORDER_TYPE_ONSALE){
			//车大大的服务为特卖商品
			
			//获取该订单关联的商品
			OnSaleRule onSaleRule = onSaleRuleRepository.findOne(serviceOrder.getGroupRuleId());
			
			int productType = onSaleRule.getProductType();
			if(productType==14){
				//在上线之前已经添加好了商品类型，规定，该商品类型表示车大大的汽车服务，需要保存订单车辆信息
				OrderCarInfo carInfo = redisRepository.getOrderCarInfo(serviceOrder.getUserId());
				carInfo.setServiceOrderId(serviceOrder.getId());//关联车辆信息和商品订单
				orderCarInfoRepository.save(carInfo);//保存订单车辆信息
			}
		}
		
	}

}
