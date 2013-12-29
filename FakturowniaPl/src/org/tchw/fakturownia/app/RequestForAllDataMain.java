package org.tchw.fakturownia.app;

import org.tchw.fakturownia.app.config.ApplicationBeanConfig;
import org.tchw.fakturownia.services.RequestForAllData;

public class RequestForAllDataMain {

    public static void main(String[] args) {
        ApplicationBeanConfig.applicationContext().getBean(RequestForAllData.class).requestForAllData();
    }

}
