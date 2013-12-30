package org.tchw.generic.stream.csvBrowsing;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class Helper {

    static void closeQuietly(Closeable bufferedReader) {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static BufferedReader bufferedReader(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ));
        return bufferedReader;
    }

}
