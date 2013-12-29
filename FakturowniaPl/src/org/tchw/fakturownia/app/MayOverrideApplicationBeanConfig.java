package org.tchw.fakturownia.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tchw.fakturownia.data.model.file.RepositoryDirectory;
import org.tchw.fakturownia.data.model.file.impl.RepositoryDirectoryImpl;
import org.tchw.fakturownia.remote.RequestExecution;
import org.tchw.fakturownia.remote.impl.RequestExecutionImpl;

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
