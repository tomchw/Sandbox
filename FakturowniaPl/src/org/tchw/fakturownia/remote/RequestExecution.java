package org.tchw.fakturownia.remote;

public interface RequestExecution {

    void doGet(String url, ResponseContentHandling contentHandling);
}