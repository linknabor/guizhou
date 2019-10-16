/**
 * 
 */
package com.yumu.hexie.model.community;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author HuYM
 *
 */
public interface ThreadRepository extends JpaRepository<Thread, Long> {

	
	public List<Thread> findByThreadStatus(String threadStatus, Pageable page);
	
	public List<Thread> findByThreadStatusAndUserSectId(String threadStatus, long userSectId, Pageable page);
	
	public List<Thread> findByThreadStatusAndUserId(String threadStatus, long userId, Pageable page);
	
	@Query(value="from Thread t where t.threadStatus = ?1 and t.userSectId = ?2 and t.threadCategory = ?3 ")
	public List<Thread> getThreadListByCategory(String threadStatus, long userSectId, String threadCategory, Pageable page);
	
	@Query(value="from Thread t where t.threadStatus = ?1 and t.threadCategory = ?2 ")
	public List<Thread> getThreadListByCategory(String threadStatus, String threadCategory, Pageable page);
	
	@Query(value="from Thread t where t.threadStatus = ?1 and t.userSectId = ?2 and t.threadCategory <> ?3 ")
	public List<Thread> getThreadListByNewCategory(String threadStatus, long userSectId, String threadCategory, Pageable page);
	
	@Query(value="from Thread t where t.threadStatus = ?1 and t.threadCategory <> ?2 ")
	public List<Thread> getThreadListByNewCategory(String threadStatus, String threadCategory, Pageable page);
	
	public List<Thread> findByThreadStatusAndUserId(String threadStatus, long userId, Sort sort);
	
	@Query(value="select thread.* from thread"
			+ " where threadStatus = 0 " 
			+ " and IF (?1!='', userName like CONCAT('%',?1,'%'), 1=1)"
			+ " and IF (?2!='', createDate = ?2, 1=1)"
			+ " and IF (?3!='', userSectId = ?3, 1=1)"
			+ " and userSectId in ?4 \n#pageable\n",
			countQuery="select count(*) from thread  where threadStatus = 0 " 
			+ " and IF (?1!='', userName like CONCAT('%',?1,'%'), 1=1)"
			+ " and IF (?2!='', createDate = ?2, 1=1)"
			+ " and IF (?3!='', userSectId = ?3, 1=1)"
			+ " and userSectId in ?4"
			,nativeQuery = true)
	public Page<Thread> getThreadList(String nickName, String createDate,String sectId,List<String> sectIds,Pageable pageable);
	
	@Modifying
	@Query(nativeQuery = true,value="update thread set threadStatus=1 where threadId in ?1")
	public int deleteThread(String[] threadIds);
}
