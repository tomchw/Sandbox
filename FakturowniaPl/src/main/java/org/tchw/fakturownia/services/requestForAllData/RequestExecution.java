package org.tchw.fakturownia.services.requestForAllData;

public interface RequestExecution {

    void doGet(String url, ResponseContentHandling contentHandling);
}