package com;

import com.step.properties.ApplicationProperties;
import com.step.properties.DruidDataSourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by user on 2019-04-17.
 */
@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class, DruidDataSourceProperties.class})
//@EnableJpaRepositories(basePackages = {"com.step.repository.primary"})
@EnableCaching
@EnableJpaAuditing
public class Application extends WebMvcConfigurationSupport {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}


