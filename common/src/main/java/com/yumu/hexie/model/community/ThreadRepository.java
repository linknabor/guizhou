/**
 * 
 */
package com.yumu.hexie.model.community;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author HuYM
 *
 */
public interface ThreadRepository extends JpaRepository<Thread, Long> {

	
	public List<Thread> findByThreadStatus(String threadStatus, Pageable page);
	
	public List<Thread> findByThreadStatusAndUserSectId(String threadStatus, long userSectId, Pageable page);
	
	@Query(value="from Thread t where t.threadStatus = ?1 and t.userSectId = ?2 and t.threadCategory = ?3 ")
	public List<Thread> getThreadListByCategory(String threadStatus, long userSectId, String threadCategory, Pageable page);
	
	@Query(value="from Thread t where t.threadStatus = ?1 and t.threadCategory = ?2 ")
	public List<Thread> getThreadListByCategory(String threadStatus, String threadCategory, Pageable page);
	
	@Query(value="from Thread t where t.threadStatus = ?1 and t.userSectId = ?2 and t.threadCategory <> ?3 ")
	public List<Thread> getThreadListByNewCategory(String threadStatus, long userSectId, String threadCategory, Pageable page);
	
	@Query(value="from Thread t where t.threadStatus = ?1 and t.threadCategory <> ?2 ")
	public List<Thread> getThreadListByNewCategory(String threadStatus, String threadCategory, Pageable page);
	
	public List<Thread> findByThreadStatusAndUserId(String threadStatus, long userId, Sort sort);
	
	public List<Thread> findByThreadStatusAndUserId(String threadStatus, long userId, Pageable page);
	
	public List<Thread> findByThreadStatusAndUserIdAndThreadCategory(String threadStatus, long userId, String category, Pageable page);
	
	@Query(value="from Thread t where t.threadStatus = ?1 and t.userId = ?2 and t.threadCategory in(?3)")
	public List<Thread> findByThreadStatusAndUserIdAndThreadCategory(String threadStatus, long userId, List<String> category, Pageable page);
}
