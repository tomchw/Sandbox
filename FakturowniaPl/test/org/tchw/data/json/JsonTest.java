package org.tchw.data.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.tchw.data.stream.Stream;

public class JsonTest {

    @Test
    public void testHandling() throws JSONException {
        MyHandling handling = new MyHandling();
        Stream.from(getClass().getResourceAsStream("simpleJson.txt")).passTo(Json.takeFromReader()).handle(handling).executeSync();
        Assert.assertEquals("1", handling.jsonArray.getJSONObject(0).getString("id") );
        Assert.assertEquals("B", handling.jsonArray.getJSONObject(1).getString("value") );
        Assert.assertEquals(2, handling.jsonArray.length() );
    }

    @Test
    public void testGettingByJSONArray() throws JSONException {
        JsonArray jsonArray = Stream.fromResource(getClass(), "simpleJson.txt").passTo(Json.takeFromReader()).asJsonArray().get();
        Assert.assertEquals("AA", jsonArray.getObject(1).getArray("children").getObject(0).getString("child_name"));
    }


    private static class MyHandling implements JSONArrayHandling {

        private JSONArray jsonArray;

        @Override
        public void handle(JSONArray jsonArray) {
            this.jsonArray = jsonArray;
        }

    }
}
