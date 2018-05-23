package com.putnam.online.travel.agent.application.config;

import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionManagersConfiguration {
    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private DataSource dataSource;

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
	JpaTransactionManager tm = new JpaTransactionManager();
	tm.setEntityManagerFactory(emf);
	tm.setDataSource(dataSource);
	return tm;
    }
}
