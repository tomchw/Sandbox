package org.tchw.fakturownia.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.tchw.fakturownia.data.model.Repository;
import org.tchw.fakturownia.data.model.file.RepositoryDirectory;
import org.tchw.fakturownia.data.model.file.impl.RepositoryDirectoryImpl;
import org.tchw.fakturownia.remote.RequestExecution;
import org.tchw.fakturownia.remote.gatherData.RequestForTableData;
import org.tchw.fakturownia.remote.gatherData.impl.RequestForTableDataToFile;
import org.tchw.fakturownia.remote.impl.RequestExecutionImpl;
import org.tchw.fakturownia.services.CalculateClientsProfits;
import org.tchw.fakturownia.services.RequestForAllData;
import org.tchw.specific.werbum.Werbum;

public class ApplicationBeanConfig {

    @Bean
    @Lazy
    public CalculateClientsProfits calculateClientsProfits() {
        return new CalculateClientsProfits(repository());
    }

    @Bean
    public RequestForAllData requestForAllData() {
        return new RequestForAllData(Werbum.login, repositoryDirectory(), requestForTableData(), requestExecution());
    }

    @Bean
    public RequestForTableData requestForTableData() {
        return new RequestForTableDataToFile(repositoryDirectory(), requestExecution());
    }

    @Bean
    public RequestExecution requestExecution() {
        return new RequestExecutionImpl();
    }

    @Bean
    public RepositoryDirectory repositoryDirectory() {
        return new RepositoryDirectoryImpl();
    }

    @Bean
    @Lazy
    public Repository repository() {
        return Repository.fromDirectoryWithToday(Werbum.directory.getPath());
    }


}
