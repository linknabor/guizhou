package com.yumu.hexie.model.extreinfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {
	//查询所有的汽车品牌
	@Query(" SELECT distinct makeName, firstLetter FROM CarInfo ORDER BY firstLetter ")
	public List<Object[]> findAllMakeName();
	//查询某品牌的所有型号
	@Query("SELECT DISTINCT modelName FROM CarInfo where makeName = ?1")
	public List<String> findAllModelNameBymakeName(String makeName);
}
