package com.app.storeInTwoDatabases.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "mysqlEntityManagerBean",             //  name of EntityManagerFactoryBean
        transactionManagerRef = "mysqlTransactionManager",              // Name of Transaction Manager bean
        basePackages = "com.app.storeInTwoDatabases.repo.mysql"         // full pkg name of repositories corresponding to this datasource
)
public class MysqlDatasourceConfig {

    @Autowired
    private Environment env;


    @Bean(name = "mysqlDatasource")
    @ConfigurationProperties(prefix = "mysql.datasource")           // properties to bind with this object
    public DataSource mysqlDatasource() {

        DriverManagerDataSource dataSource  = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("mysql.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("mysql.datasource.url"));
        dataSource.setUsername(env.getProperty("mysql.datasource.ursername"));
        dataSource.setPassword(env.getProperty("mysql.datasource.password"));

        return dataSource;
    }

    @Bean(name = "mysqlEntityManagerBean")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(mysqlDatasource())  // set data-source
                .properties(hibernateProperties())       // set hibernate properties
                .packages("com.app.storeInTwoDatabases.model.mysql")         // set model class pkg
                .persistenceUnit("mysqlPu")   // unique persistent unit name
                .build();
    }

    private Map hibernateProperties() {

        // get properties from class path and load them
        try {
            return PropertiesLoaderUtils.loadProperties(new ClassPathResource("hibernate-mysql.properties")).entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue));
        } catch (Exception e) {
            return new HashMap();
        }
    }

    @Bean(name = "mysqlTransactionManager")          // bean name for transaction manager
    public PlatformTransactionManager platformTransactionManager(@Qualifier("mysqlEntityManagerBean") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
