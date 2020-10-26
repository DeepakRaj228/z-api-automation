package com.automation.zomatoapi;

import apiEngine.EndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ZomatoCategoriesApiTest {

    @Test
    public void testGetCategoriesWithValidApiKey(){
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).get(EndPoints.categories());
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),200);
        Assert.assertNotNull(jsonobj.get("categories"),"No categories available, please check and update.");
    }

    @Test
    public void testGetCategoriesWithInValidApiKey(){
        Response resp = RestAssured.given()
                .auth().oauth2("thisisinvalidauthtoken")
                .get("https://developers.zomato.com/api/v2.1/categories");
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),403);
        Assert.assertEquals(jsonobj.get("message"),"Invalid API Key");
    }
}
