package org.tchw.fakturownia.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tchw.fakturownia.services.RequestForAllData;

public class RequestForAllDataMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        application.getBean(RequestForAllData.class).execute();
    }

}
