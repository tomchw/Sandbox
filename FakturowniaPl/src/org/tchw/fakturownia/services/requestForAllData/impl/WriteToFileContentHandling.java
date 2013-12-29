package org.tchw.fakturownia.services.requestForAllData.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.tchw.fakturownia.services.requestForAllData.ResponseContentHandling;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

public class WriteToFileContentHandling implements ResponseContentHandling {

    private final File file;

    public WriteToFileContentHandling(File file) {
        this.file = file;
    }

    @Override
    public void handleContent(final BufferedReader reader) {
        try {
            CharStreams
            .asCharSource(asInputSupplier(reader))
            .copyTo(asCharSink());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CharSink asCharSink() {
        return Files.asCharSink(file, Charsets.UTF_8);
    }

    private InputSupplier<Reader> asInputSupplier(final BufferedReader reader) {
        return new InputSupplier<Reader>() {
            @Override
            public Reader getInput() throws IOException {
                return reader;
            }
        };
    }
}
