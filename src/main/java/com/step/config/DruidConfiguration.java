package com.step.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.step.properties.DruidDataSourceProperties;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.PostgreSQL9Dialect;
import org.hibernate.dialect.SQLServer2008Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by user on 2017/6/23.
 */
@Configuration
public class DruidConfiguration {
    @Autowired
    private DruidDataSourceProperties properties;
    //主数据源
    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setInitialSize(properties.getInitialSize());
        druidDataSource.setMinIdle(properties.getMinIdle());
        druidDataSource.setMaxActive(properties.getMaxActive());
        druidDataSource.setMaxWait(properties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(properties.getValidationQuery());
        druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(properties.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setConnectionProperties(properties.getConnectionProperties());
        try {
            druidDataSource.setFilters(properties.getFilters());
            druidDataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;

    }



    @Bean("entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
        entityManagerFactoryBean.setPackagesToScan("com.step.entity");
        return entityManagerFactoryBean;
    }

    private JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        String jdbcUrl =properties.getUrl();
        if (StringUtils.contains(jdbcUrl, ":postgresql:")) {
            jpaVendorAdapter.setDatabasePlatform(PostgreSQL9Dialect.class.getName());
        }
        else if (StringUtils.contains(jdbcUrl, ":mysql:")) {
            jpaVendorAdapter.setDatabasePlatform(MySQL5InnoDBDialect.class.getName());
        }
        else if (StringUtils.contains(jdbcUrl, ":oracle:")) {
            jpaVendorAdapter.setDatabasePlatform(Oracle10gDialect.class.getName());
        }
        else if(StringUtils.contains(jdbcUrl,":sqlserver:")){
            jpaVendorAdapter.setDatabasePlatform(SQLServer2008Dialect.class.getName());
        }
        else {
            throw new IllegalArgumentException("Unknown Database of " + jdbcUrl);
        }
        return jpaVendorAdapter;
    }
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        //properties.put("hibernate.physical_naming_strategy",new SpringPhysicalNamingStrategy());
        properties.put("hibernate.show_sql",true);
        properties.put("hibernate.enable_lazy_load_no_trans",true);
//        properties.put("hibernate.hbm2ddl.auto","update");
        return properties;
    }
    @Bean("transactionManager")
    @Primary
    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactoryPrimary") EntityManagerFactory
                                                            entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }









}
