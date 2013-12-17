package org.tchw.csvBrowsing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.tchw.csvBrowsing.UsingCsvBeanReader.LineAsBeanHandler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableSortedMap;

public class CsvBrowsing {

    public static From fromStream(InputStream inputStream) {
        return new From(inputStream);
    }

    public static From fromFile(String path) {
        try {
            return new From(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static class From {

        private final InputStream inputStream;

        public From(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public void onLineAsMap(final CsvLineAsMapHandler handler) {
            UsingCsvMapReader.DEFAULT.process(inputStream, new CsvLineAsMapHandler() {
                @Override
                public void onCsvLineAsMap(ImmutableSortedMap<String, String> lineAsMap) {
                    handler.onCsvLineAsMap(lineAsMap);
                }
            });
        }

        public ImmutableList<Map<String, String>> asListOfMaps() {
            final ImmutableList.Builder<Map<String, String>> builder = ImmutableList.builder();
            UsingCsvMapReader.DEFAULT.process(inputStream, new CsvLineAsMapHandler() {
                @Override
                public void onCsvLineAsMap(ImmutableSortedMap<String, String> lineAsMap) {
                    builder.add(lineAsMap);
                }
            });
            return builder.build();
        }

        public <T> ImmutableList<T> asBeanList(Class<T> clazz) {
            final Builder<T> builder = ImmutableList.<T>builder();
            UsingCsvBeanReader.DEFAULT.process(inputStream, clazz, new LineAsBeanHandler<T>() {
                @Override
                public void onBean(T bean) {
                    builder.add(bean);
                }
            });
            return builder.build();
        }
    }



}
