package com.yumu.hexie.model.car;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarYearRepository extends JpaRepository<CarYear, Long> {

	/**获取车辆年款信息
	 * 
	 * @param brandId
	 * @param styleId
	 * @return
	 */
	public List<CarYear> findByBrandIdAndStyleId(String brandId,String styleId);
	
	/**获取车辆年款信息
	 * 
	 * @param brandId
	 * @param styleId
	 * @param yearId
	 * @return
	 */
	public CarYear findByBrandIdAndStyleIdAndYearId(String brandId,String styleId,String yearId);
}
