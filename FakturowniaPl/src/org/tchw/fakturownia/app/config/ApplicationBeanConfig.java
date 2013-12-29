package org.tchw.fakturownia.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.tchw.fakturownia.model.Repository;
import org.tchw.fakturownia.model.file.RepositoryDirectory;
import org.tchw.fakturownia.services.CalculateClientsProfits;
import org.tchw.fakturownia.services.RequestForAllData;
import org.tchw.fakturownia.services.requestForAllData.RequestExecution;
import org.tchw.fakturownia.services.requestForAllData.gatherData.RequestForTableData;
import org.tchw.fakturownia.services.requestForAllData.gatherData.impl.RequestForTableDataToFile;
import org.tchw.specific.werbum.Werbum;

@Configuration
public class ApplicationBeanConfig {

    @Autowired
    private RepositoryDirectory repositoryDirectory;

    @Autowired
    private RequestExecution requestExecution;

    @Bean
    @Lazy
    public CalculateClientsProfits calculateClientsProfits() {
        return new CalculateClientsProfits(repository());
    }

    @Bean
    public RequestForAllData requestForAllData() {
        return new RequestForAllData(Werbum.login, repositoryDirectory, requestForTableData(), requestExecution);
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

    private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationBeanConfig.class, MayOverrideApplicationBeanConfig.class);

    public static ApplicationContext applicationContext() {
        return applicationContext;
    }

}
