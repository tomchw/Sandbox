package org.tchw.fakturownia.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.tchw.fakturownia.model.Repository;
import org.tchw.fakturownia.model.file.RepositoryDirectory;
import org.tchw.fakturownia.services.CalculateClientsProfits;
import org.tchw.fakturownia.services.RequestForAllData;
import org.tchw.fakturownia.services.requestForAllData.RequestExecution;
import org.tchw.fakturownia.services.requestForAllData.RequestLogin;
import org.tchw.fakturownia.services.requestForAllData.gatherData.RequestForTableData;
import org.tchw.fakturownia.services.requestForAllData.gatherData.impl.RequestForTableDataToFile;

@Configuration
@PropertySource("classpath:fakturownia.properties")
public class ApplicationBeanConfig {

    @Autowired
    private RepositoryDirectory repositoryDirectory;

    @Autowired
    private RequestExecution requestExecution;

    @Bean
    @Lazy
    public CalculateClientsProfits calculateClientsProfits() {
        return new CalculateClientsProfits(repository(), repositoryDirectory);
    }

    @Bean
    public RequestForAllData requestForAllData() {
        return new RequestForAllData(requestLogin(), repositoryDirectory, requestForTableData(), requestExecution);
    }

    @Bean
    public RequestForTableData requestForTableData() {
        return new RequestForTableDataToFile(repositoryDirectory, requestExecution);
    }

    @Bean
    @Lazy
    public Repository repository() {
        return Repository.useRepositoryDirectory(repositoryDirectory);
    }

    @Bean
    public RequestLogin requestLogin() {
        return new RequestLogin();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    private static final AbstractApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationBeanConfig.class, MayOverrideApplicationBeanConfig.class);

    static {
        applicationContext.registerShutdownHook();
    }

    public static ApplicationContext applicationContext() {
        return applicationContext;
    }

}
