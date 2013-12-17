package org.tchw.data.csvBrowsing;

import com.google.common.collect.ImmutableSortedMap;

public interface CsvLineAsMapHandler {

    void onCsvLineAsMap(ImmutableSortedMap<String, String> lineAsMap);

}
