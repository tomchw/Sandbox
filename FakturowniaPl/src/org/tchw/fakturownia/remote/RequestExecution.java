package org.tchw.fakturownia.remote;

public interface RequestExecution {

    void execute(String url, ResponseContentHandling contentHandling);
}