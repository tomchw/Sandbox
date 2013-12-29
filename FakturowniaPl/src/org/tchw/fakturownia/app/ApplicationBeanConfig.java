package org.tchw.fakturownia.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.tchw.fakturownia.data.model.Repository;
import org.tchw.fakturownia.remote.RequestExecution;
import org.tchw.fakturownia.remote.gatherData.RequestForTableData;
import org.tchw.fakturownia.remote.gatherData.impl.RequestForTableDataToFile;
import org.tchw.fakturownia.remote.impl.RequestExecutionImpl;
import org.tchw.fakturownia.services.CalculateClientsProfits;
import org.tchw.fakturownia.services.RequestForAllData;
import org.tchw.specific.werbum.Werbum;

@Configuration
public class ApplicationBeanConfig {

    @Autowired
    private MayOverrideApplicationBeanConfig mayOverride;

    @Bean
    @Lazy
    public CalculateClientsProfits calculateClientsProfits() {
        return new CalculateClientsProfits(repository());
    }

    @Bean
    public RequestForAllData requestForAllData() {
        return new RequestForAllData(Werbum.login, mayOverride.repositoryDirectory(), requestForTableData(), requestExecution());
    }

    @Bean
    public RequestForTableData requestForTableData() {
        return new RequestForTableDataToFile(mayOverride.repositoryDirectory(), requestExecution());
    }

    @Bean
    public RequestExecution requestExecution() {
        return new RequestExecutionImpl();
    }

    @Bean
    @Lazy
    public Repository repository() {
        return Repository.fromDirectoryWithToday(Werbum.directory.getPath());
    }

    private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationBeanConfig.class, MayOverrideApplicationBeanConfig.class);

    public static ApplicationContext applicationContext() {
        return applicationContext;
    }

}
