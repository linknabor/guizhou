package com.yumu.hexie.model.localservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeBillItemRepository extends JpaRepository<HomeBillItem, Long> {

    public List<HomeBillItem> findByBillIdAndBillType(long billId,long billType);
}
