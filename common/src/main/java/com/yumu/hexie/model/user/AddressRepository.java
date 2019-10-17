package com.yumu.hexie.model.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Address, Long> {
	@Modifying@Transactional
	@Query("delete from Address a where a.id = ?1 and a.userId = ?2 and a.main=false")
	public void deleteAddress(long addressId,long user);
	
	//@Query("from Address a where a.userId = ?1") 
	public List<Address> findAllByUserId(long userId);
	
	public List<Address> findByXiaoquId(long xiaoquId);
	
	@Query("from Address a where a.userId = ?1 and a.detailAddress=?2")
	public List<Address> getAddressByuserIdAndAddress(long userId,String cell_addr);
	
	@Query("from Address a where a.userId = ?1 and a.main=?2")
	public List<Address> getAddressByMain(long userId,boolean main);
	
	@Query(nativeQuery = true, value = "select a.* from address a join user u on u.id = a.userId where u.shareCode = ?1 and a.main = '1' ")
	public List<Address> getAddressByShareCode(String shareCode);

    @Query("from Address a where a.id > 11546")
	public List<Address> getNeedAddress();
}
