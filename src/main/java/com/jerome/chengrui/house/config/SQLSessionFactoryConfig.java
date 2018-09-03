package com.jerome.chengrui.house.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SQLSessionFactoryConfig {

	private static final String CONFIG_FILE = "mysql-config.xml";
//	@Bean
	public SqlSessionFactory getSqlSessionFactory() {
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(CONFIG_FILE);
		} catch (IOException e) {
			throw new RuntimeException("Can not load config file: " + CONFIG_FILE, e);
		}
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	
}
