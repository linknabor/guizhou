package com.yumu.hexie.model.car;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarStyleRepository extends JpaRepository<CarStyle, Long> {

	/**
	 * 根据汽车品牌ID获取到车型信息列表
	 * @param brandId
	 * @return
	 */
//	@Query("from carStyle where brandId=?1")
	public List<CarStyle> findByBrandId(String brandId);
}
