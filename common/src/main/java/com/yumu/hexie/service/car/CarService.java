package com.yumu.hexie.service.car;

import java.util.List;

import com.yumu.hexie.model.car.CarBrand;
import com.yumu.hexie.model.car.CarStyle;
import com.yumu.hexie.model.car.CarYear;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.car.OrderCarInfo;

/**
 * 
 * @author hwb_work
 * <p>20160721</p>
 */
public interface CarService {

	/**
	 * 获取车辆品牌信息列表
	 * @return
	 */
	public List<CarBrand> getCarBrandList();
	
	/**
	 * 获取车辆车型信息列表
	 * @return
	 */
	public List<CarStyle> getCarStyleListByBrandId(String brandId);
	
	/**
	 * 获取车辆年款信息列表
	 * @return
	 */
	public List<CarYear> getCarYearListByBrandIdAndStyleId(String brandId,String styleId);
	
	/**
	 * 获取最新的一条车辆信息记录
	 * @param userId
	 * @return
	 */
	public OrderCarInfo getLastCarInfoByUserId(long userId);
	
	/**
	 * 保存订单车辆信息到redis中，在订单创建成功之后再添加到数据库，并关联订单信息
	 * @param carInfo 其中有carYear中的信息需要更具carInfo.yearID来获取然后重新赋值
	 * @return
	 */
	public OrderCarInfo saveTempOrderCarInfo(OrderCarInfo carInfo);
	
	/**
	 * 在订单创建成功之后添加到数据库，并关联订单信息
	 * @param serviceOrder
	 * @return
	 */
	public void saveOrderCarInfo(ServiceOrder serviceOrder);
	
}
