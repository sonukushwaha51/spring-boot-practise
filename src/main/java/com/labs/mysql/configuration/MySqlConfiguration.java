package com.labs.mysql.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "mysqlLocalContainerFactoryBean",
        transactionManagerRef = "mysqlTransactionManagerRef",
        basePackages = {"com.labs.mysql.repository"}
)
public class MySqlConfiguration {

    @Bean("mysqlDatasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.user-mysql")
    public DataSource mySqlDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean("mysqlProperties")
    public Properties mysqlProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return properties;
    }

    @Bean("mysqlLocalContainerFactoryBean")
    @Primary
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier("mysqlDatasource") DataSource mysqlDatasource,
                                                                                         EntityManagerFactoryBuilder entityManagerFactoryBuilder) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return entityManagerFactoryBuilder
                .dataSource(mysqlDatasource)
                .persistenceUnit("mysql")
                .properties(properties)
                .packages("com.labs.mysql.entity")
                .build();
    }

    @Bean("mysqlTransactionManagerRef")
    @Primary
    public PlatformTransactionManager platformTransactionManager(@Qualifier("mysqlLocalContainerFactoryBean") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean,
                                                                 @Qualifier("mysqlProperties") Properties properties) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
        jpaTransactionManager.setJpaProperties(properties);
        return jpaTransactionManager;
    }

}
