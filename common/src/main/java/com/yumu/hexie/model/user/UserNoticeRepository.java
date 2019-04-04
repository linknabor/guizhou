package com.yumu.hexie.model.user;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserNoticeRepository extends JpaRepository<UserNotice, Long> {
	
	@Query("from UserNotice u where u.userId=?1 order by createDate desc")
	public List<UserNotice> queryByUserId(long userId,Pageable page);
	
	@Modifying
	@Query("update UserNotice un set un.readed=true where un.userId=?1 and un.id=?2")
	@Transactional
	public void read(long userId,long noticeId);
}
