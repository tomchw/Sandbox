package org.tchw.fakturownia.remote;

import java.io.BufferedReader;

public interface ResponseContentHandling {

    void handleContent(BufferedReader reader);

}