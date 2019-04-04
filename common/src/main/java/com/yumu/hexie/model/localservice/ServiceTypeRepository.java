package com.yumu.hexie.model.localservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    public List<ServiceType> findByIdIn(List<Long> ids);
}
