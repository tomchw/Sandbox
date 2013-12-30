package org.tchw.fakturownia.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tchw.fakturownia.model.file.RepositoryDirectory;
import org.tchw.fakturownia.model.file.impl.RepositoryDirectoryImpl;
import org.tchw.fakturownia.services.requestForAllData.RequestExecution;
import org.tchw.fakturownia.services.requestForAllData.impl.RequestExecutionImpl;

@Configuration
public class MayOverrideApplicationBeanConfig {

    @Bean
    public RepositoryDirectory repositoryDirectory() {
        return new RepositoryDirectoryImpl();
    }

    @Bean
    public RequestExecution requestExecution() {
        return new RequestExecutionImpl();
    }
}
