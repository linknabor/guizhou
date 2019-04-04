package com.yumu.hexie.web.sales;

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
import com.yumu.hexie.model.car.CarBrand;
import com.yumu.hexie.model.car.CarStyle;
import com.yumu.hexie.model.car.CarYear;
import com.yumu.hexie.model.market.car.OrderCarInfo;
import com.yumu.hexie.model.redis.RedisRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.car.CarService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

/**
 * 汽车维修服务，针对车大大的服务，订单存到serviceOrder中，在创建订单之前，保存用户填写的车辆信息
 * @author hwb_work
 *
 */
@Controller(value="cdd")
public class CarController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(CarController.class);

	@Inject
	private CarService carService;
	
	/**
	 * 获取汽车品牌信息列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/carBrandlist", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<CarBrand>> carBreadList() throws Exception {
		List<CarBrand> carBrandList = carService.getCarBrandList();
		return new BaseResult<List<CarBrand>>().success(carBrandList);
    }
	
	/**
	 * 根据汽车品牌编号获取车型信息列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/carStylelist/{brandId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<CarStyle>> carStylelist(@PathVariable String brandId) throws Exception {
		List<CarStyle> carStyleList = carService.getCarStyleListByBrandId(brandId);
		return new BaseResult<List<CarStyle>>().success(carStyleList);
	}
	
	/**
	 * 根据车型编号获取车型年款信息列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/carYearlist/{brandId}/{styleId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<CarYear>> carYearlist(@PathVariable String brandId,@PathVariable String styleId) throws Exception {
		List<CarYear> carYearList = carService.getCarYearListByBrandIdAndStyleId(brandId,styleId);
		return new BaseResult<List<CarYear>>().success(carYearList);
	}
	
	/**
	 * 根据车型编号获取车型年款信息列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/lastCarInfo/", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<OrderCarInfo> carYearlist(@ModelAttribute(Constants.USER)User user) throws Exception {
		OrderCarInfo carInfo = carService.getLastCarInfoByUserId(user.getId());
		return new BaseResult<OrderCarInfo>().success(carInfo);
	}
	
	/**
	 * 临时保存订单中的车辆信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTempOrderCarInfo", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<OrderCarInfo> saveTempOrderCarInfo(@RequestBody OrderCarInfo carInfo,@ModelAttribute(Constants.USER)User user) throws Exception {
		try {
			carInfo.setUserId(user.getId());
			carInfo = carService.saveTempOrderCarInfo(carInfo);
			return new BaseResult<OrderCarInfo>().success(carInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e+"");
			return new BaseResult<OrderCarInfo>().failMsg("车辆信息保存失败，请重新保存！");
		}
	}
}
