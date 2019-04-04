package com.yumu.hexie.model.localservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {

    public List<ServiceItem> findByIdIn(List<Long> ids);
    
    public List<ServiceItem> findByType(long type);
}
