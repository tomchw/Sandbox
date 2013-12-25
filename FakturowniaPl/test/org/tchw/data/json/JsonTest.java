package org.tchw.data.json;

import org.junit.Assert;
import org.junit.Test;
import org.tchw.data.stream.Stream;

public class JsonTest {

    @Test
    public void testAsJsonArray() {
        JsonArray jsonArray = Stream.from(getClass().getResourceAsStream("simpleJson.txt")).passTo(Json.takeFromReader()).asJsonArray();
        Assert.assertEquals("1", jsonArray.getObject(0).getString("id") );
        Assert.assertEquals("B", jsonArray.getObject(1).getString("value") );
        Assert.assertEquals(2, jsonArray.length() );
    }

}
