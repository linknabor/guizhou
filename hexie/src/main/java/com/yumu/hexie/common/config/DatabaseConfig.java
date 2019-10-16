package com.yumu.hexie.common.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class DatabaseConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

	@Value(value = "${jdbc.dialect}")
	private String dialect;
	@Value(value = "${jdbc.url}")
	private String url;
	@Value(value = "${jdbc.username}")
	private String userName;
	@Value(value = "${jdbc.password}")
	private String password;
	@Value(value = "${jdbc.driverClassName}")
	private String driverClassName;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("com.yumu.hexie.model");
		factoryBean.setDataSource(jpaDataSource());
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(true);
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setDatabasePlatform(dialect);
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		return factoryBean;
	}

	@Bean
	public DataSource jpaDataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setJdbcUrl(url);
		dataSource.setUser(userName);
		dataSource.setPassword(password);
		try {
			dataSource.setDriverClass(driverClassName);
			dataSource.setMaxPoolSize(450);
			dataSource.setMinPoolSize(5);
			dataSource.setMaxIdleTime(1200);
		} catch (PropertyVetoException e) {
			logger.error("Can not create Data source.");
		}
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
