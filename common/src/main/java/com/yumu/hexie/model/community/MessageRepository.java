package com.yumu.hexie.model.community;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {
	@Query("from Message m where m.msgType=?1 and ((m.regionType=0) "
			+ "or (m.regionType=1 and m.regionId=?2) "
			+ "or (m.regionType=2 and m.regionId=?3) "
			+ "or (m.regionType=3 and m.regionId=?4) "
			+ "or (m.regionType=4 and m.regionId=?5)) "
			+ "and m.status=0 "
			+ "order by m.createDate desc")
	public List<Message> queryMessageByRegions(int type, long provinceId, long cityId,
			long countyId, long xiaoquId, Pageable pageable);
	
	
	@Query("from Message m where m.status = 0 and m.msgType=?1 order by m.top desc, m.createDate desc ")
	public List<Message> queryMessagesByStatus(int msgType, Pageable pageable);

	@Query("from Message m where m.status = 0 and m.msgType=?1 and m.regionId in(?2) order by m.top desc, m.createDate desc ")
	public List<Message> queryMessagesByStatus(int msgType, List<String> id, Pageable pageable);
	
	@Query("from Message m where m.status = 0 and m.msgType=?1 and m.regionId =?2 order by m.top desc, m.createDate desc ")
	public Message queryMessagesByReginId(int msgType, String regionId);
	
}
