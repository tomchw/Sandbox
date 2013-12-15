package org.tchw.csvBrowsing;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;


public class CsvBrowsing {

    public static From fromStream(InputStream inputStream) {
        return new From(inputStream);
    }

    public static class From {

        private final InputStream inputStream;

        public From(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public <T> ImmutableList<T> asBeanList(Class<T> clazz) {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ));
            CsvBeanReader csvBeanReader = new CsvBeanReader(bufferedReader, CsvPreference.STANDARD_PREFERENCE);
            try {
                String[] header = csvBeanReader.getHeader(true);
                Builder<T> builder = ImmutableList.<T>builder();
                T read;
                while((read=csvBeanReader.read(clazz, header))!=null) {
                    builder.add(read);
                };
                return builder.build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                closeQuietly(bufferedReader);
                closeQuietly(csvBeanReader);
            }
        }

    }

    private static void closeQuietly(Closeable bufferedReader) {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
