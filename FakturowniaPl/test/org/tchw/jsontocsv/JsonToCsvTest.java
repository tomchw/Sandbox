package org.tchw.jsontocsv;

import org.junit.Test;

import static org.junit.Assert.fail;

public class JsonToCsvTest {

    @Test
    public void test() {
        JsonToCsv.fromFileToSimilarFile("c:/Private/Work/Werbum/firma-ksiegarska-werbum.products.json.4.txt").executeSync();
        //JsonToCsv.fromStream(getClass().getResourceAsStream("tcc1.products.json.1.txt")).toScreen().executeSync();
        fail("Not yet implemented");
    }

}
