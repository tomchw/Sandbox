package org.tchw.fakturownia.services.requestForAllData;

import java.io.BufferedReader;

public interface ResponseContentHandling {

    void handleContent(BufferedReader reader);

}