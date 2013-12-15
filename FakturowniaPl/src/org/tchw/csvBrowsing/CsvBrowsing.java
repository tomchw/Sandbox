package org.tchw.csvBrowsing;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Collection;

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
            ImmutableList<String> fieldNames = declaredFieldNames(clazz);

            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ));
            CsvBeanReader csvReader = new CsvBeanReader(bufferedReader, CsvPreference.STANDARD_PREFERENCE);
            try {
                String[] headerWithNulls = headerWithNullsIfThereIsNoSuchField(csvReader.getHeader(true), fieldNames);
                Builder<T> builder = ImmutableList.<T>builder();
                T read;
                while((read=csvReader.read(clazz, headerWithNulls))!=null) {
                    builder.add(read);
                };
                return builder.build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                closeQuietly(bufferedReader);
                closeQuietly(csvReader);
            }
        }

        private String[] headerWithNullsIfThereIsNoSuchField(String[] header, Collection<String> fieldNames) {
            String[] result = new String[header.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = fieldNames.contains(header[i]) ? header[i] : null;
            }
            return result;
        }

        private <T> ImmutableList<String> declaredFieldNames(Class<T> clazz) {
            Builder<String> builder = ImmutableList.<String>builder();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                builder.add(field.getName());
            }
            return builder.build();
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
