package com.yumu.hexie.model.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
	public List<SystemConfig> findAllBySysKey(String key);
}
