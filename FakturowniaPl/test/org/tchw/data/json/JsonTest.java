package org.tchw.data.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

public class JsonTest {

    @Test
    public void testHandling() throws JSONException {
        MyHandling handling = new MyHandling();
        Json.from(getClass().getResourceAsStream("simpleJson.txt")).handle(handling).executeSync();
        Assert.assertEquals("1", handling.jsonArray.getJSONObject(0).getString("id") );
        Assert.assertEquals("B", handling.jsonArray.getJSONObject(1).getString("value") );
        Assert.assertEquals(2, handling.jsonArray.length() );
    }

    @Test
    public void testGettingByJSONArray() throws JSONException {
        JSONArray jsonArray = Json.fromResource(getClass(), "simpleJson.txt").asJSONArray().get();
        Assert.assertEquals("AA", jsonArray.getJSONObject(1).getJSONArray("children").getJSONObject(0).getString("child_name"));
    }


    private static class MyHandling implements JSONArrayHandling {

        private JSONArray jsonArray;

        @Override
        public void handle(JSONArray jsonArray) {
            this.jsonArray = jsonArray;
        }

    }
}
