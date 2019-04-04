package com.yumu.hexie.model.user;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	@Query("from Feedback m where m.articleId=?1 order by m.createDate desc")
	List<Feedback> findAllByArticleId(long articleId,Pageable pager);
}
