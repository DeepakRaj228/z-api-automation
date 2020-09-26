package com.automation.zomatoapi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ZomatoRestaurantApiTest extends BaseSetup{

    @Test
    public void testGetRestaurantInfoWithValidResId(){
        int res_id = 16507326;
        Response resp = RestAssured.given()
                .spec(getBaseRequestSpecification()).queryParam("res_id",res_id).get("/restaurant");
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),200);
        Assert.assertNotNull(jsonobj.get("R"),"No Restaurants available in this restaurant id, please check and update.");
    }

    @Test
    public void testGetRestaurantInfoWithInValidResId(){
        int res_id = -13231;
        Response resp = RestAssured.given()
                .spec(getBaseRequestSpecification()).queryParam("res_id",res_id).get("/restaurant");
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),404);
        Assert.assertEquals(jsonobj.get("message"),"Not Found");
    }

    @Test
    public void testGetRestaurantInfoWithResIdAsZero(){
        int res_id = 0;
        Response resp = RestAssured.given()
                .spec(getBaseRequestSpecification()).queryParam("res_id",res_id).get("/restaurant");
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),404);
        Assert.assertEquals(jsonobj.get("message"),"Not Found");
    }

    @Test
    public void testGetRestaurantInfoWithValidResIdWithoutResInfo(){
        int res_id = 22;
        Response resp = RestAssured.given()
                .spec(getBaseRequestSpecification()).queryParam("res_id",res_id).get("/restaurant");
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),404);
        Assert.assertEquals(jsonobj.get("message"),"Not Found");
    }
}
