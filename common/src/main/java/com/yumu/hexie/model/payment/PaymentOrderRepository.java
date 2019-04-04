package com.yumu.hexie.model.payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    public List<PaymentOrder> findAllByPaymentNo(String paymentNo);

    public List<PaymentOrder> findByOrderTypeAndOrderId(int orderType, long orderId);

    public List<PaymentOrder> findByOrderTypeAndOrderIdAndOpenId(int orderType, long orderId,String openId);
    
    @Query("from PaymentOrder p where p.status = "+PaymentConstant.PAYMENT_STATUS_INIT + " and p.createDate<?1")
    public List<PaymentOrder> findTimeoutPaymentOrder(long timeLast);
    
    @Query("select p.orderId from PaymentOrder p where p.status = "+PaymentConstant.PAYMENT_STATUS_INIT +" and p.orderType="+PaymentConstant.TYPE_MARKET_ORDER+" order by p.createDate desc")
    public List<Long> queryAllUnpayMarketOrderIds();
}
