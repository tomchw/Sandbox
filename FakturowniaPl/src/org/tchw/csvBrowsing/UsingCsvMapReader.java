package org.tchw.csvBrowsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.supercsv.io.CsvMapReader;
import org.supercsv.prefs.CsvPreference;

import com.google.common.collect.ImmutableMap;

class UsingCsvMapReader {

    final static UsingCsvMapReader DEFAULT = new UsingCsvMapReader();

    public void process(InputStream inputStream, LineAsMapHandler lineAsMapHandler) {
        BufferedReader bufferedReader = Helper.bufferedReader(inputStream);
        CsvMapReader csvReader = new CsvMapReader(bufferedReader, CsvPreference.STANDARD_PREFERENCE);
        try {
            String[] header = csvReader.getHeader(true);
            Map<String, String> read = null;
            try {
                while((read=csvReader.read(header))!=null) {
                    lineAsMapHandler.onLineAsMap(ImmutableMap.copyOf(read));
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

    interface LineAsMapHandler {

        void onLineAsMap(ImmutableMap<String, String> lineAsMap);

    }
}
