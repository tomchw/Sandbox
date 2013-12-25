package org.tchw.fakturownia.main;

import java.io.File;

import org.tchw.fakturownia.remote.GetRequest.Login;
import org.tchw.fakturownia.remote.gatherData.RequestForTableData;
import org.tchw.specific.werbum.Werbum;

public class RequestForAllData {

    private final Login login;

    public RequestForAllData(Login login) {
        this.login = login;
    }

    public void execute() {
        File directory = new File("c:/Private/Work/Werbum/abc");
        new RequestForTableData(directory, login.clients(), "clients").execute();
        new RequestForTableData(directory, login.products(), "products").execute();
        new RequestForTableData(directory, login.invoices(), "tempInvoices").execute();
    }

    public static void main(String[] args) {
        new RequestForAllData(Werbum.login).execute();
    }

}
