package com.yumu.hexie.model.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BottomIconRepository extends JpaRepository<BottomIcon, Long> {

	List<BottomIcon> findByIconSys(String iconSys, Sort sort);
}
