package com.yumu.hexie.common.config;

import java.io.File;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@Configuration
@ComponentScan(basePackages = {"com.yumu.hexie"}, includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = {"com.yumu.hexie.web.*","com.yumu.hexie.service.*"}))
@EnableJpaRepositories({"com.yumu.hexie.model.*"})
@EnableScheduling
@EnableAspectJAutoProxy
@EnableAsync
@EnableCaching(proxyTargetClass=true)
public class AppConfig {

    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }
    
    @Value("${testMode}")
    private Boolean testMode;
    
    @Bean
    public EmbeddedServletContainerFactory EmbeddedServletContainerFactory(){
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(85);
        factory.addConnectorCustomizers(new AppTomcatConnectorCustomizer());
        if (!Boolean.TRUE.equals(testMode)) {
        	factory.addAdditionalTomcatConnectors(createSslConnector());
		}
        return factory;
    }
    
    private Connector createSslConnector() {
    	
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        try {
            File truststore = new File("F:/keystore/server.jks");
            connector.setScheme("https");
            protocol.setSSLEnabled(true);
            connector.setSecure(true);
            connector.setPort(8443);
            protocol.setKeystoreFile(truststore.getAbsolutePath());
            protocol.setKeystorePass("hongzhitech20130110");
            return connector;
        } catch (Exception ex) {
            throw new IllegalStateException("cant access keystore: [" + "keystore" + "]  ", ex);
        }
    }
    
    
   
}
