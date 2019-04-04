package com.yumu.hexie.model.commonsupport.gotong;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SmsHisRepository extends JpaRepository<SmsHis, Long> {
	
	@Query(value="select * from SmsHis s where s.phone = ?1 and s.code is not null and s.code != '' order by sendDate desc limit 1", nativeQuery = true)
	public SmsHis findByPhoneLatest(String phone);
	
	@Query(value="select count(s.id) from SmsHis s where s.phone = ?1 and messageType = ?2 and sendDate >= ?3 ")
	public int findByPhoneAndMesssageTypeInOneMonth(String phone, int messsageType, Date date);
	
	
}
