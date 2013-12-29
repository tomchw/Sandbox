package org.tchw.fakturownia.app.requestForAllDataMainTest;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.tchw.fakturownia.model.file.RepositoryDirectory;
import org.tchw.fakturownia.services.requestForAllData.RequestExecution;

import com.google.common.io.Files;

public class TestApplicationBeanConfig {

    @Bean
    public RepositoryDirectory repositoryDirectory() {
        return new RepositoryDirectory() {

            private final File file = Files.createTempDir();

            @Override
            public File repositoryDirectory() {
                return file;
            }
        };
    }

    @Bean
    public RequestExecution requestExecution() {
        return new TestRequestExecution();
    }
}
