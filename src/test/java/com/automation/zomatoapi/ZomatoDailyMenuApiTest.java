package com.automation.zomatoapi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ZomatoDailyMenuApiTest extends BaseSetup{

    @Test
    public void testGetDailyMenuWithValidResId(){
        int res_id = 16507326;
        Response resp = RestAssured.given()
                .spec(getBaseRequestSpecification()).queryParam("res_id",res_id).get("/dailymenu");
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),200);
        Assert.assertNotNull(jsonobj.get("daily_menus"),"No daily_menus available in this restaurant, please check and update.");
    }

    @Test
    public void testGetDailyMenuWithInValidResId(){
        int res_id = -13231;
        Response resp = RestAssured.given()
                .spec(getBaseRequestSpecification()).queryParam("res_id",res_id).get("/dailymenu");
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),400);
        Assert.assertEquals(jsonobj.get("message"),"No Daily Menu Available");
    }

    @Test
    public void testGetDailyMenuWithResIdAsZero(){
        int res_id = 0;
        Response resp = RestAssured.given()
                .spec(getBaseRequestSpecification()).queryParam("res_id",res_id).get("/dailymenu");
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),400);
        Assert.assertEquals(jsonobj.get("message"),"Invalid res_id. Please try again with correct Zomato's restaurant ID.");
    }

    @Test
    public void testGetDailyMenuWithValidResIdWithoutDailyMenus(){
        int res_id = 19368400;
        Response resp = RestAssured.given()
                .spec(getBaseRequestSpecification()).queryParam("res_id",res_id).get("/dailymenu");
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),400);
        Assert.assertEquals(jsonobj.get("message"),"No Daily Menu Available");
    }
}
