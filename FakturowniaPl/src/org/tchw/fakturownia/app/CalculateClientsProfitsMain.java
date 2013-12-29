package org.tchw.fakturownia.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tchw.fakturownia.services.CalculateClientsProfits;

public class CalculateClientsProfitsMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(ApplicationBeanConfig.class);
        CalculateClientsProfits calculateClientsProfits = application.getBean(CalculateClientsProfits.class);
        calculateClientsProfits.calculateClientsProfits();
    }

}
