package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HomeServiceRepository extends JpaRepository<HomeService, Long> {

	//根据精选来判断
	@Query("from HomeService p where p.isHandpick=1 and p.status=1 order by p.sortNo asc,p.id desc ")
	public List<HomeService> findByisHandpick();

	//根据商品类型来判断
	@Query("from HomeService p where p.serviceType = ?1 and p.status=1 order by p.sortNo asc,p.id desc ")
	public List<HomeService> findByServiceType(int serviceType);
}
