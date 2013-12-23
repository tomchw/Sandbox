package org.tchw.data.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.common.base.Preconditions;

public class Stream {

    public static BufferedReader toBufferedReader(Reader reader) {
        if( reader instanceof BufferedReader ) {
            return (BufferedReader) reader;
        } else {
            return new BufferedReader(reader);
        }
    }

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
        InputStream resourceAsStream = clazz.getResourceAsStream(resourceName);
        Preconditions.checkNotNull(resourceAsStream, "Resource " + resourceName + " does not exist for class " + clazz.getSimpleName());
        return new From(resourceAsStream);
    }

    public static class From {

        private final InputStream inputStream;

        public From(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public interface ReaderPasser<T> {
            T pass(Reader tokener);
        }

        public BufferedReader asBufferedReader() {
            return new BufferedReader(new InputStreamReader(inputStream));
        }

        public <T> T passTo(ReaderPasser<T> passer) {
            return passer.pass(asBufferedReader());
        }
    }
}
