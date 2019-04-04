package com.yumu.hexie.model.localservice.bill;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yumu.hexie.model.localservice.HomeServiceConstant;

public interface YunXiyiBillRepository extends JpaRepository<YunXiyiBill, Long> {

    public Page<YunXiyiBill> findByUserId(long userId, Pageable page);
    
    @Query("From YunXiyiBill b where b.status = " + HomeServiceConstant.ORDER_STATUS_CREATE
        +" AND b.createDate < ?1")
    public List<YunXiyiBill> findTimeoutBill(long latestTime);
}
