package com.yumu.hexie.model.distribution.region;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
	//根据商品类型来判断
	@Query("from Merchant p where p.productType = ?1")
	public Merchant findMerchantByProductType(int productType);
}
