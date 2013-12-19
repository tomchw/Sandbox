package org.tchw.data.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Stream {

    public static From from(InputStream inputStream) {
        return new From(inputStream);
    }

    public static From from(File file) {
        try {
            return new From(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static From fromFile(String filePath) {
        try {
            return new From(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static From fromResource(Class<?> clazz, String resourceName) {
        return new From(clazz.getResourceAsStream(resourceName));
    }

    public static class From {

        private final InputStream inputStream;

        public From(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public interface ReaderPasser<T> {
            T pass(Reader tokener);
        }

        public AsReader asBufferedReader() {
            return new AsReader(inputStream);
        }

        public class AsReader {

            private final BufferedReader reader;

            public AsReader(InputStream inputStream) {
                reader = new BufferedReader(new InputStreamReader(inputStream));
            }

            public Reader get() {
                return reader;
            }

            public <T> T passTo(ReaderPasser<T> passer) {
                return passer.pass(reader);
            }
        }
    }
}
