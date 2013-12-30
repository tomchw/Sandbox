package org.tchw.specific.tcc1;

import org.tchw.fakturownia.services.requestForAllData.GetRequest;
import org.tchw.fakturownia.services.requestForAllData.GetRequest.Login;

public class Tcc1DataLoad {

    public static void main(String[] args) {
        Login werbum = GetRequest.login("tcc1", "sMEuDnemiZIPcbEL5g");
        //werbum.invoices().page(1).saveContentToFile();
    }
}
