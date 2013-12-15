package org.tchw.jsontocsv;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Splitter;

public class JsonToCsvTest {

    @Test
    public void test() {
        String csvString = JsonToCsv.fromStream(getClass().getResourceAsStream("simpleJson.txt")).asStringNow();
        Iterator<String> iterator = Splitter.onPattern("(\r\n|\n)").splitToList(csvString).iterator();
        Assert.assertEquals("id,value", iterator.next());
        Assert.assertEquals("1,A", iterator.next());
        Assert.assertEquals("2,B", iterator.next());
        Assert.assertEquals("",    iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

}
