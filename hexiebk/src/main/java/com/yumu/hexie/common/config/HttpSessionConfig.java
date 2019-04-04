package com.yumu.hexie.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 360000, redisNamespace = "qibao")
public class HttpSessionConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpSessionConfig.class);
    @Value(value = "${redis.host}")
    private String host;
    @Value(value = "${redis.port}")
    private Integer port;
    @Value(value = "${redis.password}")
    private String password;
    @Value(value = "${redis.database}")
    private Integer database;
    
    @Bean
    public JedisConnectionFactory connectionFactory() {
    	LOGGER.error("JedisConnectionFactory connectionFactory()");
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setPassword(password);
        factory.setDatabase(database);
        return factory;
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
    	LOGGER.error("HttpSessionStrategy httpSessionStrategy()");
        //return new HeaderHttpSessionStrategy();
    	return new CookieHttpSessionStrategy();
    }
}
