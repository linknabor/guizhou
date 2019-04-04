package com.yumu.hexie.model.market;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yumu.hexie.model.ModelConstant;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
	
	@Query("from ServiceOrder p left outer join fetch p.items where p.id=?1")
	public ServiceOrder findOneWithItem(long orderId);
	@Query("from ServiceOrder p where p.status = "+ModelConstant.ORDER_STATUS_INIT + " and p.closeTime<?1")
	public List<ServiceOrder> findTimeoutServiceOrder(long timeLast);
	
	@Query(value = "select * from ServiceOrder p where p.userId = ?1 and p.status in ?2 order by id desc", nativeQuery = true)
	public List<ServiceOrder> findByUserAndStatus(long userId,List<Integer> statuses);
	
	@Query(value = "select * from ServiceOrder p where p.userId = ?1 and p.status in ?2 and p.orderType in ?3 order by id desc", nativeQuery = true)
	public List<ServiceOrder> findByUserAndStatusAndTypes(long userId,List<Integer> statuses, List<Integer> types);

	@Query(value = "select * from ServiceOrder p where p.userId = ?1 and p.status in ?2 and p.orderType = ?3 order by id desc", nativeQuery = true)
	public List<ServiceOrder> findByUserAndStatusAndType(long userId,List<Integer> statuses, int orderType);	
	
	public ServiceOrder findByOrderNo(String orderNo);

	public List<ServiceOrder> findAllByTel(String tel);

	public List<ServiceOrder> findAllByStatus(int status,Pageable page);
	public int countByStatus(int status);
	

	@Query("from ServiceOrder p where p.status != "+ModelConstant.ORDER_STATUS_CANCEL + " and p.groupRuleId=?1 and p.orderType=" + ModelConstant.ORDER_TYPE_RGROUP)
	public List<ServiceOrder> findByRGroup(long ruleId);

	public List<ServiceOrder> findAllByProductId(long productId);
	
	@Query(" from ServiceOrder p where p.userId =?1 and p.status in ?2 and p.merchantId = ?3 and p.orderType = ?4 ")
	public List<ServiceOrder> findByUserAndStatusAndMerchatIdAndOrderType(long userId, List<Integer> statuses, long merchantId, int orderType);
	
	
	@Query(" from ServiceOrder p where p.userId =?1 and p.status in ?2 and p.productId = ?3 and p.orderType = ?4 ")
	public List<ServiceOrder> findByUserAndStatusAndProductIdAndOrderType(long userId, List<Integer> statuses, long productId, int orderType);
	
	@Query(" from ServiceOrder p where p.status in ?1 and p.productId = ?2 and p.orderType = ?3 ")
	public List<ServiceOrder> CheckCountByStatusAndProductIdAndOrderType(List<Integer> statuses, long productId, int orderType);
	
	@Query(" from ServiceOrder p where p.userId =?1 and p.status in ?2 and p.groupRuleId = ?3 and p.orderType = ?4 ")
	public List<ServiceOrder> CheckByUserAndStatusAndRuleIdAndOrderType(long userId, List<Integer> statuses, long ruleId, int orderType);
	
	@Query(value=" select s.id from serviceorder s join orderitem i on s.id = i.orderId where s.userId = ?1 and i.productId = ?2 and s.status =?3 group by s.id ", nativeQuery = true)
	public List<ServiceOrder> findAllByUserAndStatusAndProductIdAndOrderType(long userId, long productId, List<Integer> statuses);
}
