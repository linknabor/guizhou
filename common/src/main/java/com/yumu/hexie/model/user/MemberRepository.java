package com.yumu.hexie.model.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long>{
	List<Member> findByUserid(long userid);
	Member findById(long billid);
	
	@Query(value = "select * from member where enddate<?1",nativeQuery = true)
	List<Member> getAllEndDate(String enddate);
}
