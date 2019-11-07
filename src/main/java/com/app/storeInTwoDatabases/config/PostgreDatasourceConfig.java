package com.app.storeInTwoDatabases.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
import java.util.Objects;
import java.util.stream.Collectors;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgreEntityManagerBean",                   // name of EntityManagerFactoryBean
        transactionManagerRef = "postgreTransactionManager",                    // Name of Transaction Manager bean
        basePackages = "com.app.storeInTwoDatabases.repo.postgre"               // full pkg name of repositories corresponding to this datasource
)
public class PostgreDatasourceConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean(name="postgreDatasource")
    @ConfigurationProperties(prefix = "postgre.datasource")    // properties to bind with this datasource
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource  = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("postgre.datasource.driver-class-name")));
        dataSource.setUrl(env.getProperty("postgre.datasource.url"));
        dataSource.setUsername(env.getProperty("postgre.datasource.ursername"));
        dataSource.setPassword(env.getProperty("postgre.datasource.password"));

        return dataSource;
    }

    @Primary
    @Bean(name = "postgreEntityManagerBean")
    public LocalContainerEntityManagerFactoryBean  localContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder builder)
    {
        return builder
                .dataSource(dataSource())     // set data-source
                .properties(hibernateProperties())          // set hibernate properties
                .packages("com.app.storeInTwoDatabases.model.postgre")          // set model class pkg
                .persistenceUnit("postgrePu")               // unique persistent unit name
                .build();           // build
    }

    private Map hibernateProperties() {

        // get properties from class path and load them
        try {
            return PropertiesLoaderUtils.loadProperties(new ClassPathResource("hibernate-postgre.properties")).entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey().toString(), Map.Entry::getValue));
        }
        catch (Exception e)
        {
            return new HashMap();
        }
    }

    @Primary
    @Bean(name="postgreTransactionManager")             // bean name for transaction manager
    public PlatformTransactionManager platformTransactionManager(@Qualifier("postgreEntityManagerBean") EntityManagerFactory entityManagerFactory)
    {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
