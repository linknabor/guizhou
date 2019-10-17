package com.yumu.hexie.model.community;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageSectRepository extends JpaRepository<MessageSect, Long> {

	public List<MessageSect> findByMessageId(Long messageId);
	
}
