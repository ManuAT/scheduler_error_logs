package com.nectar.failurelogsys.db.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;



@Configuration 

public class DatabaseHistoryConfig   {

    @Autowired
	private Environment env;

	@Bean
	@Primary
	public DataSource firstDataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource2.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource2.url"));
		dataSource.setUsername(env.getProperty("spring.datasource2.username"));
		dataSource.setPassword(env.getProperty("spring.datasource2.password"));
		return dataSource;
	}

	@Bean
	public DataSource secondDataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.history_db.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.history_db.url"));
		dataSource.setUsername(env.getProperty("spring.history_db.username"));
		dataSource.setPassword(env.getProperty("spring.history_db.password"));
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplateOne(@Qualifier("firstDataSource") DataSource ds)
	{
		return new JdbcTemplate(ds);
	}

	@Bean()
	public JdbcTemplate jdbcTemplateTwo(@Qualifier("secondDataSource") DataSource ds)
	{
		return new JdbcTemplate(ds);
	}


}
