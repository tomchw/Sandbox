package org.tchw.fakturownia.app;

import org.springframework.context.annotation.Bean;
import org.tchw.fakturownia.data.model.Repository;
import org.tchw.fakturownia.services.CalculateClientsProfits;
import org.tchw.fakturownia.services.RequestForAllData;
import org.tchw.specific.werbum.Werbum;

public class ApplicationBeanConfig {

    @Bean
    public CalculateClientsProfits calculateClientsProfits() {
        return new CalculateClientsProfits(repository());
    }

    @Bean
    public RequestForAllData requestForAllData() {
        return new RequestForAllData(Werbum.login);
    }

    @Bean
    public Repository repository() {
        return Repository.fromDirectoryWithToday(Werbum.directory.getPath());
    }


}
