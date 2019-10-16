package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface HaoJiaAnCommentRepository extends JpaRepository<HaoJiaAnComment, Long>{
	//根据订单id和评论类型查看当前订单是否有被评论和投诉
	@Query(value = "select * from haojiaancomment c where c.yuyueOrderNo = ?1 and c.commentType = ?2", nativeQuery = true)
	public HaoJiaAnComment getCommentByOrderNoAndType(String yuyueOrderNo, int commentType);
	
	//获得投诉详情信息
	@Query(value = "SELECT o.`address`,o.`tel`,o.`status`,o.`productName`,o.`orderNo`,c.complainStatus,c.commentContent FROM `haojiaancomment` c INNER JOIN `yuyueorder` o ON o.`orderNo` = c.yuyueOrderNo"
			+ " WHERE c.id = ?1", nativeQuery = true)
	public List<Object[]> getComplainDetail(long commentId);
	
}
