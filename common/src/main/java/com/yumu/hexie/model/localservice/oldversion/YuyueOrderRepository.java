package com.yumu.hexie.model.localservice.oldversion;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YuyueOrderRepository extends JpaRepository<YuyueOrder, Long> {
//	@Query("from YuyueOrder y where y.serviceOrderId !=0 and y.userId =?1") 
	public List<YuyueOrder> findAllByUserId(long userId, Sort sort);

	public YuyueOrder findByServiceOrderId(long serviceOrderId);
	
	//jpa查询规范之根据订单号查询订单
	public YuyueOrder findByOrderNo(String orderNo);
}
