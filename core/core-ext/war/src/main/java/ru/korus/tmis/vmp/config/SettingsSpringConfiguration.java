package ru.korus.tmis.vmp.config;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;


import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "coreSettingsEntityManagerFactory",
        transactionManagerRef = "coreSettingsTransactionManager",
        basePackages = { "ru.korus.tmis.vmp.repositories.settings" })
public class SettingsSpringConfiguration {

    @Bean(name = "coreSettingsDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(VmpSpringConfiguration.dataBaseType.getDriver());
        dataSource.setUrl(VmpSpringConfiguration.dataBaseType.getUrlCoreSettings());

        dataSource.setUsername(VmpSpringConfiguration.getUsername(VmpSpringConfiguration.POOL_NAME_TMIS_CORE));
        dataSource.setPassword(VmpSpringConfiguration.getUsername(VmpSpringConfiguration.POOL_NAME_TMIS_CORE));

        return dataSource;
    }

    @Bean(name = "coreSettingsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean.setPackagesToScan(VmpSpringConfiguration.PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN + ".settings");
        entityManagerFactoryBean.setJpaProperties(VmpSpringConfiguration.hibProperties(VmpSpringConfiguration.dataBaseType.getUrlCoreSettings()));

        return entityManagerFactoryBean;
    }

    @Bean(name = "coreSettingsTransactionManager")
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

}
