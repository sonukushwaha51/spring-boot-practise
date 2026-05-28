package com.labs.postgres.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgresqlLocalContainerFactoryBean",
        transactionManagerRef = "postgresqlTransactionManagerRef",
        basePackages = {"com.labs.postgres.repository"}
)
public class PostgresqlConfiguration {

    @Bean("postgresqlDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.book-postgres")
    public DataSource postgresqlDatasource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean("postgresqlProperties")
    public Properties postgresqlProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return properties;
    }

    @Bean("postgresqlLocalContainerFactoryBean")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier("postgresqlDatasource") DataSource postgresqlDatasource,
                                                                                         EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return entityManagerFactoryBuilder
                .dataSource(postgresqlDatasource)
                .persistenceUnit("postgresql")
                .properties(properties)
                .packages("com.labs.postgres.entity")
                .build();
    }

    @Bean("postgresqlTransactionManagerRef")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("postgresqlLocalContainerFactoryBean") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean,
                                                                 @Qualifier("postgresqlProperties") Properties properties) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
        jpaTransactionManager.setJpaProperties(properties);
        return jpaTransactionManager;
    }

}
