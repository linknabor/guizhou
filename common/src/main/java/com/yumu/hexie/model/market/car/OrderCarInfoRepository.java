package com.yumu.hexie.model.market.car;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderCarInfoRepository extends JpaRepository<OrderCarInfo, Long> {
	

	/**
	 * 获取用户最新的一条车辆信息记录
	 * @param userId
	 * @return
	 */
//	@Query( value = " select * from OrderCarInfo o where o.userId = ?1 ", nativeQuery = true)
//	public List<OrderCarInfo> getCarInfoByUserId(long userId);
	@Query(value="select * from orderCarInfo where id IN (select max(id) from ordercarInfo where userid = ?1)", nativeQuery = true)
	public List<OrderCarInfo> getCarInfoByUserId(long userId);
}
