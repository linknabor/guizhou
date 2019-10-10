package com.yumu.hexie.model.user;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempUserRepository extends JpaRepository<TempUser, Long>{
	
	public List<TempUser> findByType(String type, Pageable page);
	
	public List<TempUser> findById(Long id);
	
	public List<TempUser> findBySectid(String sectid);
	
	
	
}
