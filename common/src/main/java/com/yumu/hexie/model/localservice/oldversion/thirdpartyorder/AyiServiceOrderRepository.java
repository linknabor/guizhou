package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.market.ServiceOrder;

public interface AyiServiceOrderRepository extends JpaRepository<AyiServiceOrder, Long> {
	@Query("from ServiceOrder p where p.status = "+ModelConstant.ORDER_STATUS_INIT + " and p.closeTime<?1")
	public List<ServiceOrder> findTimeoutServiceOrder(long timeLast);

	@Query(value = "select * from ServiceOrder p where p.userId = ?1 and p.status in ?2", nativeQuery = true)
	public List<ServiceOrder> findByUserAndStatus(long userId,List<Integer> statuses);
}
