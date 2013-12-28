package org.tchw.fakturownia.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tchw.fakturownia.main.CalculateClientsProfits;

public class CalculateClientsProfitsMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        CalculateClientsProfits calculateClientsProfits = application.getBean(CalculateClientsProfits.class);
        calculateClientsProfits.execute();
    }

}
