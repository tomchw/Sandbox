package org.tchw.csvBrowsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collection;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

class UsingCsvBeanReader {

    final static UsingCsvBeanReader DEFAULT = new UsingCsvBeanReader();

    public <T> void process(InputStream inputStream, Class<T> beanClass, LineAsBeanHandler<T> handler) {
        ImmutableList<String> fieldNames = declaredFieldNames(beanClass);

        BufferedReader bufferedReader = Helper.bufferedReader(inputStream);
        CsvBeanReader csvReader = new CsvBeanReader(bufferedReader, CsvPreference.STANDARD_PREFERENCE);
        try {
            String[] headerWithNulls = headerWithNullsIfThereIsNoSuchField(csvReader.getHeader(true), fieldNames);
            T read;
            while((read=csvReader.read(beanClass, headerWithNulls))!=null) {
                handler.onBean(read);
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Helper.closeQuietly(bufferedReader);
            Helper.closeQuietly(csvReader);
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

    interface LineAsBeanHandler<T> {

        void onBean(T bean);

    }
}

