package org.tchw.data.jsontocsv;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.tchw.data.json.Json;
import org.tchw.data.json.csv.JsonToCsv;

import com.google.common.base.Splitter;

public class JsonToCsvTest {

    @Test
    public void test() {
        String csvString = Json.from(getClass().getResourceAsStream("simpleJson.txt")).passTo(JsonToCsv.takeFromJson()).asStringNow();
        Iterator<String> iterator = Splitter.onPattern("(\r\n|\n)").splitToList(csvString).iterator();
        Assert.assertEquals("id,value", iterator.next());
        Assert.assertEquals("1,A", iterator.next());
        Assert.assertEquals("2,B", iterator.next());
        Assert.assertEquals("", iterator.next());
        Assert.assertFalse("", iterator.hasNext());
    }

    @Test
    public void test2() {
        Json.from(getClass().getResourceAsStream("simpleJson.txt")).passTo(JsonToCsv.takeFromJson()).toScreen().executeSync();
    }

}
