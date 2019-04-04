package com.yumu.hexie.model.view;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PageConfigViewRepository extends JpaRepository<PageConfigView, Long> {

    public PageConfigView findByTempKey(String key);
}
