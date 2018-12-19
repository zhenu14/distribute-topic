package com.cdl.multisource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongli.chi
 * @date 2018/12/19
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryPostgresql",
        transactionManagerRef="transactionManagerPostgresql",
        basePackages= { "com.cdl.multisource.repository.postgresql" })
public class PostgresqlConfig {

    @Autowired
    @Qualifier("postgresqlDataSource")
    private DataSource postgresqlDataSource;

    // 获取对应的数据库方言
    @Value("${spring.jpa.hibernate.postgresql-dialect}")
    private String postgresqlDialect;


    @Bean(name = "entityManagerPostgresql")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryPostgresql(builder).getObject().createEntityManager();
    }
    @Bean(name = "entityManagerFactoryPostgresql")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPostgresql (EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(postgresqlDataSource)
                .properties(getVendorProperties())
                .packages("com.cdl.multisource.model") //设置实体类所在位置
                .persistenceUnit("postgresqlPersistenceUnit")
                .build();
    }

    @Autowired
    private JpaProperties jpaProperties;
    private Map getVendorProperties() {
        // 设置对应的数据库方言
        Map<String,String> map = new HashMap<>();
        map.put("hibernate.dialect",postgresqlDialect);
        jpaProperties.setProperties(map);
        return jpaProperties.getHibernateProperties(new HibernateSettings());
    }

    @Bean(name = "transactionManagerPostgresql")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPostgresql(builder).getObject());
    }

}
