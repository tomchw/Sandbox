package org.tchw.fakturownia.services;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.tchw.fakturownia.data.model.file.RepositoryDirectory;

public class TestApplicationBeanConfig {

    @Bean
    public RepositoryDirectory repositoryDirectory() {
        return new RepositoryDirectory() {

            private int counter;

            @Override
            public File repositoryDirectory() {
                counter++;
                return new File(counter + ".txt");
            }
        };
    }
}
