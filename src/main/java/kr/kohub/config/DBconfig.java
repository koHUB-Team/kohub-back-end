package kr.kohub.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties" )
public class DBconfig {
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(byEnvironment("datasource.mysql.driver"));
		dataSource.setUrl(byEnvironment("datasource.mysql.url"));
		dataSource.setUsername(byEnvironment("datasource.mysql.user"));
		dataSource.setPassword(byEnvironment("datasource.mysql.password"));
		return dataSource;
	}
	
	private String byEnvironment(String before) {
        String key = before + "." + env.getProperty("environment");
        return env.getProperty(key);
    }
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
//	public PlatformTransactionManager annotationDrivenTransactionManager() {
//		return transactionManager();
//	}
}
