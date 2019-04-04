package com.yumu.hexie.model.localservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceItemTagRepository extends JpaRepository<ServiceItemTag, Long> {
    
    public List<ServiceItemTag> findByItemId(long itemId);
}
