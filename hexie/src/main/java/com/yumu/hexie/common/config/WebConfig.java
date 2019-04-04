package com.yumu.hexie.common.config;

import java.nio.charset.Charset;
import java.util.List;

import javax.xml.transform.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumu.hexie.web.interceptor.CheckUserAddedInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan({"com.yumu.hexie.web"})
public class WebConfig extends WebMvcConfigurationSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);
    private static final String PROP_FILE_ENCODING = "UTF-8";

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
        handlerMapping.setRemoveSemicolonContent(false);
        return handlerMapping;
    }

    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	converters.clear();
    	MappingJackson2HttpMessageConverter c = new MappingJackson2HttpMessageConverter(){
        	public boolean canWrite(MediaType mediaType) {
        		if(super.canWrite(mediaType)) {
        			return true;
        		} else if(MediaType.APPLICATION_FORM_URLENCODED.equals(mediaType)){
      				return true;
        		} else if(MediaType.APPLICATION_JSON.equals(mediaType)){
      				return true;
        		} else {
        			return false;
        		}
        	}
        };
    	converters.add(c);
    	converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
    	converters.add(new ByteArrayHttpMessageConverter());
    	converters.add(new ResourceHttpMessageConverter());
    	converters.add(new SourceHttpMessageConverter<Source>());
    	converters.add(new AllEncompassingFormHttpMessageConverter());
    	converters.add(new FormHttpMessageConverter());
	}
    @Bean
    public static PropertySourcesPlaceholderConfigurer loadProperties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[]{new ClassPathResource("config.properties")};
        configurer.setLocations(resources);
        configurer.setFileEncoding(PROP_FILE_ENCODING);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/css/**").addResourceLocations("/resources/css/");
        registry.addResourceHandler("/resources/img/**").addResourceLocations("/resources/img/");
        registry.addResourceHandler("/resources/js/**").addResourceLocations("/resources/js/");
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("locale/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    @Override  
    protected void addInterceptors(InterceptorRegistry registry) {  
    	LOGGER.error("addInterceptors start");  
        registry.addInterceptor(checkUserInterceptor());  
        LOGGER.error("addInterceptors end");  
    }
    
    @Bean
    public HandlerInterceptor checkUserInterceptor(){
    	return new CheckUserAddedInterceptor();
    }
    @Bean(name = "mapper")
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }
}