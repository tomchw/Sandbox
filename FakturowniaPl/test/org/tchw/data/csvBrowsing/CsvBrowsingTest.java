package org.tchw.data.csvBrowsing;

import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.tchw.data.csvBrowsing.CsvBrowsing;

import com.google.common.collect.ImmutableList;

public class CsvBrowsingTest {

    @Test
    public void csvBrowsingAsBean() {
        ImmutableList<MyBean> asBeanList = CsvBrowsing.fromStream(getClass().getResourceAsStream("a.csv")).asBeanList(MyBean.class);
        Iterator<MyBean> iterator = asBeanList.iterator();
        Assert.assertEquals("1", iterator.next().getId());
        Assert.assertEquals("B", iterator.next().getValue());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void csvBrowsingAsListOfMap() {
        ImmutableList<Map<String, String>> asListOfMap = CsvBrowsing.fromStream(getClass().getResourceAsStream("a.csv")).asListOfMaps();
        Iterator<Map<String, String>> iterator = asListOfMap.iterator();
        Assert.assertEquals("1", iterator.next().get("id"));
        Assert.assertEquals("B", iterator.next().get("value"));
        Assert.assertFalse(iterator.hasNext());
    }

    public static class MyBean {

        private String id;

        private String value;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }


    }
}
