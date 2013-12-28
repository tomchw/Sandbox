package org.tchw.fakturownia.data.example;

import org.tchw.fakturownia.remote.GetRequest;
import org.tchw.fakturownia.remote.GetRequest.Login;

public class Tcc1DataLoad {

    public static void main(String[] args) {
        Login werbum = GetRequest.login("tcc1", "sMEuDnemiZIPcbEL5g");
        //werbum.invoices().page(1).saveContentToFile();
    }
}
