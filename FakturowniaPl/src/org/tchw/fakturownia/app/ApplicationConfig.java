package org.tchw.fakturownia.app;

import org.springframework.context.annotation.Bean;
import org.tchw.fakturownia.data.model.Repository;
import org.tchw.fakturownia.main.CalculateClientsProfits;
import org.tchw.specific.werbum.Werbum;

public class ApplicationConfig {

    @Bean
    public CalculateClientsProfits calculateClientsProfits() {
        return new CalculateClientsProfits(repository());
    }

    @Bean
    public Repository repository() {
        return Repository.fromDirectoryWithToday(Werbum.directory.getPath());
    }


}
