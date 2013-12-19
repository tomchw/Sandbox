package org.tchw.fakturownia.api.cases.example;

import org.tchw.fakturownia.api.core.GetRequest;
import org.tchw.fakturownia.api.core.GetRequest.Login;

public class WerbumDataLoad {

    public static void main(String[] args) {
        Login werbum = GetRequest.login("firma-ksiegarska-werbum", "kKDYjBlyJvJ5siEABL");
        //werbum.invoices().page(1).printContentToSreen().executeSync();
        werbum.invoice(1251024).page(1).printContentToSreen().executeSync();
    }

}
