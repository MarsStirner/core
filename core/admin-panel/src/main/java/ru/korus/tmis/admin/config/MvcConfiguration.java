package ru.korus.tmis.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.korus.tmis.admin.controller.AuthInterceptor;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.database.common.DbOrganizationBeanLocal;
import ru.korus.tmis.core.database.common.DbSettingsBeanLocal;
import ru.korus.tmis.core.notification.DbNotificationActionBeanLocal;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;

@Configuration
@ComponentScan(basePackages = "ru.korus.tmis.admin")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    AuthInterceptor authInterceptor;

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/admin/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
    }

    @Bean
    public AuthStorageBeanLocal getAuthStorageBeanLocal() throws NamingException {
        return getEjbWrapper().getAuthStorageBeanLocal();
    }

    @Bean
    public DbSettingsBeanLocal getDbSettingsBean() throws NamingException {
        return getEjbWrapper().getDbSettingsBean();
    }

    @Bean
    public DbOrganizationBeanLocal getOrganizationBeanLocal() throws NamingException {
        return getEjbWrapper().getOrganizationBeanLocal();
    }

    @Bean
    public DbNotificationActionBeanLocal getDbNotificationActionBean() throws NamingException {
        return getEjbWrapper().getDbNotificationActionBeanLocal();
    }

    private EjbWrapperLocal getEjbWrapper() throws NamingException {
        String jndi = String.format("java:app/admin-panel-%s/EjbWrapper", ConfigManager.Common().version());
        return (EjbWrapperLocal) (new InitialContext()).lookup(jndi);
    }
}
