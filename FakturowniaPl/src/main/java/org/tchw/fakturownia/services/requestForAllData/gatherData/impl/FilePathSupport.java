package org.tchw.fakturownia.services.requestForAllData.gatherData.impl;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

class FilePathSupport {

    private final File file;

    public FilePathSupport(String filePath) {
        file = new File(filePath);
    }

    public boolean areThereMorePages() {
        String firstLine = readFirstLineQuietly();
        if( "[]".equals(firstLine) ) {
            file.delete();
            return false;
        } else {
            return true;
        }
    }

    private String readFirstLineQuietly() {
        String firstLine;
        try {
            firstLine = Files.readFirstLine(file, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return firstLine;
    }
}
