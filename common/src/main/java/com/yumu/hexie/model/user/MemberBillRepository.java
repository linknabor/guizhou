package com.yumu.hexie.model.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBillRepository extends JpaRepository<MemberBill, Long>{
	public MemberBill findByMemberbillid(String memberbillid);
	
	public List<MemberBill> findByStatus(String status);
	
	public List<MemberBill> findByUserid(long userid);
}
