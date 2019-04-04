package com.yumu.hexie.model.payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RefundOrderRepository extends JpaRepository<RefundOrder, Long> {
	public List<RefundOrder> findAllByRefundNo(String refundNo);
	public List<RefundOrder> findAllByPaymentNo(String paymentNo);
	
	@Query("from RefundOrder r where r.refundStatus=" +PaymentConstant.REFUND_STATUS_APPLYED + " and r.createDate<?1")
	public List<RefundOrder> findAllApplyedRefund(long lastTime);
}
