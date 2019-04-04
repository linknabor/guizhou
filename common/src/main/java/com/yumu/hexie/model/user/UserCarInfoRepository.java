package com.yumu.hexie.model.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserCarInfoRepository extends JpaRepository<UserCarInfo, Long> {
	@Modifying
	@Query("delete from UserCarInfo a where a.id = ?1 and a.userId = ?2")
	public void deleteCar(long UserCarInfoId,long user);
	
	//@Query("from Address a where a.userId = ?1") 
	public List<UserCarInfo> findAllByUserId(long userId);
}
