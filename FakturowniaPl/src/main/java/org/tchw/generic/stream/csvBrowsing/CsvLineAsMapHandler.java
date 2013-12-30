package org.tchw.generic.stream.csvBrowsing;

import com.google.common.collect.ImmutableSortedMap;

public interface CsvLineAsMapHandler {

    void onCsvLineAsMap(ImmutableSortedMap<String, String> lineAsMap);

}
