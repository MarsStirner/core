package ru.korus.tmis.core.ext.config;

import org.hibernate.ejb.HibernatePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.korus.tmis.core.ext.config.entities.DataSourceSettings;
import ru.korus.tmis.core.ext.config.entities.Meta;
import ru.korus.tmis.core.ext.config.entities.Settings;
import ru.korus.tmis.core.ext.config.entities.SettingsResponse;
import ru.korus.tmis.core.ext.controller.AuthInterceptor;

import javax.naming.ConfigurationException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "ru.korus.tmis.core.ext")
@EnableWebMvc
@EnableJpaRepositories("ru.korus.tmis.core.ext.repositories.s11r64")
public class MainSpringConfiguration extends WebMvcConfigurerAdapter {
    public static final String SYS_PROP_NAME_CONFIGURATION_SERVICE_URL = "hitsl.configService.url";
    public static final String DEFAULT_VALUE_CONFIGURATION_SERVICE_URL = "http://www.hitsl-config-service.ru";
    public static final String SYS_PROP_NAME_CONFIGURATION_SERVICE_CONFIG_NAME = "hitsl.configService.configName";
    public static final String DEFAULT_VALUE_CONFIGURATION_SERVICE_CONFIG_NAME = "CORE_EXT";
    public static final String PROPERTY_NAME_HIBERNATE_DIALECT_MYSQL = "org.hibernate.dialect.MySQL5InnoDBDialect";
    public static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "ru.korus.tmis.core.ext.entities";

    private static final Logger LOG = LoggerFactory.getLogger("CONFIGURATION");
    @Autowired
    private AuthInterceptor authInterceptor;

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }


    @Bean(name = "ConfigManager")
    public ConfigManager configManager() throws ConfigurationException {
        LOG.info("Start initializing ConfigManager");
        final String configurationServiceUrl = System.getProperty(SYS_PROP_NAME_CONFIGURATION_SERVICE_URL, DEFAULT_VALUE_CONFIGURATION_SERVICE_URL);
        final String configurationName = System.getProperty(
                SYS_PROP_NAME_CONFIGURATION_SERVICE_CONFIG_NAME, DEFAULT_VALUE_CONFIGURATION_SERVICE_CONFIG_NAME
        );
        final String fullUrl = configurationServiceUrl.concat("/").concat(configurationName);
        LOG.info("FULL URL = \'{}\'", fullUrl);
        final RestTemplate template = new RestTemplate();
        final SettingsResponse settingsResponse;
        try {
            settingsResponse = template.getForObject(fullUrl, SettingsResponse.class);
        } catch (Exception e) {
            LOG.error("CRITICAL_ERROR: Cannot initialize settings from URL: \'{}\'", fullUrl, e);
            throw e;
        }
        if (settingsResponse != null) {
            final Meta meta = settingsResponse.getMeta();
            if (meta != null && HttpStatus.valueOf(meta.getCode()).is2xxSuccessful()) {
                final Settings settings = settingsResponse.getResult();
                if (settings != null) {
                    LOG.info("Settings from Configuration_Service: {}", settings);
                    ConfigManager result = new ConfigManager(settings);
                    LOG.info("After processing config is: {}", result);
                    //TODO check method on result
                    return result;
                } else {
                    LOG.error("Settings is not parsed correctly.");
                    throw new ConfigurationException("Incorrect settings format");
                }
            } else {
                LOG.error("CRITICAL_ERROR: Cannot initialize settings from URL: \'{}\'. Meta is not 200 : {}", fullUrl, meta);
                throw new ConfigurationException("Incorrect settings");
            }
        } else {
            LOG.error("CRITICAL_ERROR: Cannot initialize settings from URL: \'{}\'. Null response", fullUrl);
            throw new ConfigurationException("Null response");
        }
    }

    @Bean
    public DataSource dataSource() throws ConfigurationException {
        final DataSourceSettings dataSourceSettings = configManager().getDatasource();
        if (dataSourceSettings != null) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(dataSourceSettings.getDriverClassName());
            dataSource.setUrl(dataSourceSettings.getConnectionUrl());
            dataSource.setUsername(dataSourceSettings.getUserName());
            dataSource.setPassword(dataSourceSettings.getPassword());
            LOG.info("DataSource configured: {}", dataSource);
            return dataSource;
        } else {
            throw new ConfigurationException("Datasource not configured properly");
        }
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws ConfigurationException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean.setPackagesToScan(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN + ".s11r64");
        entityManagerFactoryBean.setJpaProperties(hibProperties(configManager().getDatasource()));

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws ConfigurationException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    public Properties hibProperties(DataSourceSettings cfg) throws ConfigurationException {
        if (cfg != null) {
            Properties properties = new Properties();
//        properties.put("hibernate.hbm2ddl.auto", "NONE");
            properties.put("hibernate.dialect", PROPERTY_NAME_HIBERNATE_DIALECT_MYSQL);
            properties.put("hibernate.show_sql", System.getProperty("hibernate.show_sql", "false").equals("true"));
            properties.put("hibernate.globally_quoted_identifiers", "true");
            properties.put("hibernate.connection.useUnicode", "true");
            properties.put("hibernate.connection.characterEncoding", "UTF-8");
            properties.put("hibernate.connection.charSet", "UTF-8");

            properties.put("hibernate.connection.url", cfg.getConnectionUrl());
            properties.put("hibernate.connection.username", cfg.getUserName());
            properties.put("hibernate.connection.password", cfg.getPassword());
            properties.put("hibernate.connection.zeroDateTimeBehavior", "convertToNull");

            properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
            properties.put("hibernate.auto_close_session", "true");
            properties.put("hibernate.connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
            properties.put("hibernate.c3p0.acquire_increment", 5);
            properties.put("hibernate.c3p0.idle_test_period", 1800);
            properties.put("hibernate.c3p0.max_size", 600);
            properties.put("hibernate.c3p0.max_statements", 50);
            properties.put("hibernate.c3p0.min_size", 5);
            properties.put("hibernate.c3p0.timeout", 1800);
            properties.put("hibernate.c3p0.numHelperThreads", 10);
            properties.put("hibernate.c3p0.maxAdministrativeTaskTime", 15);
            properties.put("hibernate.enable_lazy_load_no_trans", "true");
            return properties;
        } else {
            throw new ConfigurationException("Datasource not configured properly");
        }
    }
}
