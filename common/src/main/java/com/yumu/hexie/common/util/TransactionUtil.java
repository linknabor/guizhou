package com.yumu.hexie.common.util;

import java.util.function.Consumer;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 复杂事务调用这个类。普通事务请用{@link Transactional}
 * 
 * @author huym
 * @param <T>
 *
 */
@Component
public class TransactionUtil<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionUtil.class);

	@Inject
	private PlatformTransactionManager transactionManager;

	public boolean transact(Consumer<T> consumer) {
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			consumer.accept(null);
			transactionManager.commit(status);
			return true;
		} catch (Exception e) {
			transactionManager.rollback(status);
			logger.error(e.toString(),e);
			return false;
		}

	}

}
