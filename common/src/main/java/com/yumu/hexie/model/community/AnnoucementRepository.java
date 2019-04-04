/**
 * 
 */
package com.yumu.hexie.model.community;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HuYM
 * 社区公告
 *
 */
public interface AnnoucementRepository extends JpaRepository<Annoucement, Long> {

	public List<Annoucement>findAll(Sort sort);
	
}
