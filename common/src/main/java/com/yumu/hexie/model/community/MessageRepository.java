package com.yumu.hexie.model.community;

import java.util.List;

import org.springframework.data.domain.Page;
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
	
	
	@Query(" from Message m where m.status = 0 order by m.top desc, m.createDate desc ")
	public List<Message> queryMessagesByStatus(Pageable pageable);
	
	@Query(value = "select distinct m.* from message m "
			+ "join messageSect ms on ms.messageId = m.id "
			+ "where m.status = ?1 and if( ?2 != '', m.id = ?2, 1=1 )"
			+ "and if( ?3 != '', m.title like CONCAT('%',?3,'%') , 1=1 ) and if( ?4 != '', m.publishDate >= ?4, 1=1 ) "
			+ "and if ( ?5 != '', m.publishDate <= ?5, 1=1) and ms.sectId in ?6 "
			+ "order by m.top desc, m.createDate desc "
			+ " \n#pageable\n ", 
			countQuery = "select count(*) from ( select distinct m.* from message m "
					+ "join messageSect ms on ms.messageId = m.id "
					+ "where m.status = ?1 and if( ?2 != '', m.id = ?2, 1=1 ) "
					+ "and if( ?3 != '', m.title like %?3%, 1=1 ) and if( ?4 != '', m.publishDate >= ?4, 1=1 ) "
					+ "and if ( ?5 != '', m.publishDate <= ?5, 1=1) and ms.sectId in ?6 ) a",
			nativeQuery = true)
	public Page<Message> queryMessageMutipleCons(int status, long id, String title, 
			String startDate, String endDate, List<String> sectIds, Pageable pageable);
	
	/**
	 * 查询全平台的资讯,msgType=9的
	 * @param msgType
	 * @param pageable
	 * @return
	 */
	@Query(value="select m.id,'' as content,m.createDate,m.msgType,m.title,m.summary,m.fromSite,m.regionType,m.regionId,m.publishDate,m.status,m.top,m.image,m.smallImage from message m where m.status = 0 and m.msgType = 9 order by m.top desc, m.createDate desc  \n#pageable\n ", nativeQuery = true)
	public List<Message> queryMessagesByStatusAndMsgType(Pageable pageable);
	
	@Query(value = "select distinct m.id,'' as content,m.createDate,m.msgType,m.title,m.summary,m.fromSite,m.regionType,m.regionId,m.publishDate,m.status,m.top,m.image,m.smallImage from message m join messageSect ms on m.id = ms.messageId "
			+ "where m.status = 0 and ms.sectId = ?1 and m.msgType = ?2 order by m.top desc, m.createDate desc "
			+ "\n#pageable\n", nativeQuery = true)
	public List<Message> queryMessagesByUserAndType(String sectId, int msgType, Pageable pageable);
	
	
}
