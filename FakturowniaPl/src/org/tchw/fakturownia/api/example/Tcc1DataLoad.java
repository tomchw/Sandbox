package org.tchw.fakturownia.api.example;

import org.tchw.fakturownia.api.core.GetRequest;
import org.tchw.fakturownia.api.core.GetRequest.Login;

public class Tcc1DataLoad {

    public static void main(String[] args) {
        Login werbum = GetRequest.login("tcc1", "sMEuDnemiZIPcbEL5g");
        werbum.invoices().page(1).writeContentToFile().executeSync();
    }
}
