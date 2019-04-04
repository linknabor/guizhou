package com.yumu.hexie.model.commonsupport.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	//根据商品ID查询评论，按照id反序
	@Query("from Comment p where p.productId = ?1 order by p.id desc ")
	public List<Comment> QueryByProductId(long productId);
}
