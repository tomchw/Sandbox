package org.tchw.csvBrowsing;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

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

    public static class MyBean {

        private Number id;

        private String value;

        public Number getId() {
            return id;
        }

        public void setId(Number id) {
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
