package org.tchw.generic.data.csvBrowsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.supercsv.io.CsvMapReader;
import org.supercsv.prefs.CsvPreference;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedMap.Builder;

class UsingCsvMapReader {

    final static UsingCsvMapReader DEFAULT = new UsingCsvMapReader();

    public void process(InputStream inputStream, CsvLineAsMapHandler lineAsMapHandler) {
        BufferedReader bufferedReader = Helper.bufferedReader(inputStream);
        CsvMapReader csvReader = new CsvMapReader(bufferedReader, CsvPreference.STANDARD_PREFERENCE);
        try {
            String[] header = csvReader.getHeader(true);
            Map<String, String> read = null;
            try {
                while((read=csvReader.read(header))!=null) {
                    lineAsMapHandler.onCsvLineAsMap(nullValuesToEmptyValues(read));
                };
            } catch (RuntimeException e) {
                throw new RuntimeException("Last read: " + read, e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Helper.closeQuietly(bufferedReader);
            Helper.closeQuietly(csvReader);
        }
    }

    private static ImmutableSortedMap<String, String> nullValuesToEmptyValues(Map<String, String> map) {
        Builder<String, String> builder = ImmutableSortedMap.naturalOrder();
        for (Entry<String, String> entry : map.entrySet()) {
            builder.put(entry.getKey(), Strings.nullToEmpty(entry.getValue()));
        }
        return builder.build();
    }
}
