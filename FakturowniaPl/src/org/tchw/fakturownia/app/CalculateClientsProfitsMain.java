package org.tchw.fakturownia.app;

import org.tchw.fakturownia.services.CalculateClientsProfits;

public class CalculateClientsProfitsMain {

    public static void main(String[] args) {
        ApplicationBeanConfig.applicationContext().getBean(CalculateClientsProfits.class).calculateClientsProfits();
    }

}
