package com.automation.zomatoapi;

import apiEngine.EndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ZomatoCuisineApiTest {

    @Test
    public void testGetCuisinesWithValidCityId(){
        int city_id = 7;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("city_id",city_id).get(EndPoints.cuisines());
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),200);
        Assert.assertNotNull(jsonobj.get("cuisines"),"No cuisines in this region, please check.");
    }

    @Test
    public void testGetCuisinesWithCityIdAsZero(){
        int city_id = 0;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("city_id",city_id).get(EndPoints.cuisines());
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),400);
        Assert.assertEquals(jsonobj.get("message").toString(),"Invalid coordinates / city_id");
    }

    @Test
    public void testGetCuisinesWithInvalidCityId(){
        int city_id = -86;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("city_id",city_id).get(EndPoints.cuisines());
        Assert.assertEquals(resp.getStatusCode(),500);
    }

    @Test
    public void testGetCuisinesWithLatOnly(){
        double latitude = 13.0827;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("lat",latitude).get(EndPoints.cuisines());
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),400);
        Assert.assertEquals(jsonobj.get("message").toString(),"Invalid coordinates / city_id");
    }

    @Test
    public void testGetCuisinesWithLonOnly(){
        double longitude = 80.2707;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("lon",longitude).get(EndPoints.cuisines());
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),400);
        Assert.assertEquals(jsonobj.get("message").toString(),"Invalid coordinates / city_id");
    }

    @Test
    public void testGetCuisinesWithLatLonOnly(){
        double latitude = 13.0827;
        double longitude = 80.2707;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("lat",latitude).
                        queryParam("lon",longitude).get(EndPoints.cuisines());
        Assert.assertEquals(resp.getStatusCode(),200);
    }

    @Test
    public void testGetCuisinesWithLatLonCityOfSameRegion(){
        double latitude = 13.0827;
        double longitude = 80.2707;
        int city_id = 7;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("city_id",city_id).queryParam("lat",latitude).
                        queryParam("lon",longitude).get(EndPoints.cuisines());
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertNotNull(jsonobj.get("cuisines"),"No cuisines in this region, please check.");
        Assert.assertEquals(resp.getStatusCode(),200);
    }

    @Test
    public void testGetCuisinesWithLatLonCityOfDifferentRegion(){
        double latitude = 12.9716;
        double longitude = 77.5946;
        int city_id = 7;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("city_id",city_id).queryParam("lat",latitude).
                        queryParam("lon",longitude).get(EndPoints.cuisines());
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertNotNull(jsonobj.get("cuisines"),"No cuisines in this region, please check.");
        Assert.assertEquals(resp.getStatusCode(),200);
    }
}
