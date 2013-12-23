package org.tchw.data.json.csv;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.tchw.data.json.Json;
import org.tchw.data.stream.Stream;

import com.google.common.base.Splitter;

public class JsonToCsvTest {

    @Test
    public void test() {
        String csvString = Stream.from(getClass().getResourceAsStream("simpleJson.txt")).passTo(Json.takeFromReader()).asJsonArray().passTo(JsonToCsv.takeFromJsonArray()).asString();
        Iterator<String> iterator = Splitter.onPattern("(\r\n|\n)").splitToList(csvString).iterator();
        Assert.assertEquals("id,value", iterator.next());
        Assert.assertEquals("1,A", iterator.next());
        Assert.assertEquals("2,B", iterator.next());
        Assert.assertEquals("", iterator.next());
        Assert.assertFalse("", iterator.hasNext());
    }

}
