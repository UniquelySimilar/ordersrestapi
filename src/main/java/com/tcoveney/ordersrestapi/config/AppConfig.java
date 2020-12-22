package com.tcoveney.ordersrestapi.config;

import java.util.List;
import java.util.Properties;

import javax.naming.NamingException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tcoveney.ordersrestapi.model.Customer;
import com.tcoveney.ordersrestapi.model.LineItem;
import com.tcoveney.ordersrestapi.model.Order;
import com.tcoveney.ordersrestapi.model.Product;
import com.tcoveney.ordersrestapi.model.TokenUser;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan(basePackages = "com.tcoveney")
public class AppConfig implements WebMvcConfigurer {	
	// NOTE: DataSource properties are set in 'application.properties'
	@Bean
	@ConfigurationProperties("app.datasource")
	public HikariDataSource dataSource() {
	    return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Bean
	LocalSessionFactoryBean hibernateSessionFactory() throws NamingException {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setAnnotatedClasses(Customer.class, Order.class, TokenUser.class, LineItem.class, Product.class);
		sessionFactory.setHibernateProperties(hibernateProperties());
		
		return sessionFactory;
	}
	
	@SuppressWarnings("serial")
	Properties hibernateProperties() {
		return new Properties() {
			{
				setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			}
		};
	}
	
	@Bean
	HibernateTransactionManager transactionManager() throws NamingException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(hibernateSessionFactory().getObject());
		return transactionManager;
	}
	
	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource
	      = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:validation-messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}

    /* Here we register the Hibernate5Module into an ObjectMapper, then set this custom-configured ObjectMapper
     * to the MessageConverter and return it to be added to the HttpMessageConverters of our application*/
    public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        //Registering Hibernate5Module to support lazy objects
        mapper.registerModule(new Hibernate5Module());
        
        // Register JavaTimeModule to support Java 8 Date and Time API
        mapper.registerModule(new JavaTimeModule());

        messageConverter.setObjectMapper(mapper);
        return messageConverter;

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //Here we add our custom-configured HttpMessageConverter
        converters.add(jacksonMessageConverter());
    }

}
