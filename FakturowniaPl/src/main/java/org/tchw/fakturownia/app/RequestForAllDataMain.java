package org.tchw.fakturownia.app;

import org.tchw.fakturownia.services.RequestForAllData;

public class RequestForAllDataMain {

    public static void main(String[] args) {
        ApplicationMain.applicationContext().getBean(RequestForAllData.class).requestForAllData();
    }

}
