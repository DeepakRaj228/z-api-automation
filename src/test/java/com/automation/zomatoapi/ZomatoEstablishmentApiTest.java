package com.automation.zomatoapi;

import apiEngine.EndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ZomatoEstablishmentApiTest {

    @Test
    public void testGetEstablishmentsWithValidCityId(){
        int city_id = 7;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("city_id",city_id).get(EndPoints.establishments());
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),200);
        Assert.assertNotNull(jsonobj.get("establishments"),"No establishments in this region, please check.");
    }

    @Test
    public void testGetEstablishmentsWithCityIdAsZero(){
        int city_id = 0;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("city_id",city_id).get(EndPoints.establishments());
        Assert.assertEquals(resp.getStatusCode(),400);
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(jsonobj.get("message").toString(),"Invalid or missing parameter \"city_id\"");
    }

    @Test
    public void testGetEstablishmentsWithInValidCityId(){
        int city_id = -86;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("city_id",city_id).get(EndPoints.establishments());
        Assert.assertEquals(resp.getStatusCode(),400);
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(jsonobj.get("message").toString(),"Invalid or missing parameter \"city_id\"");
    }

    @Test
    public void testGetEstablishmentsWithLatOnly(){
        double latitude = 13.0827;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("lat",latitude).get(EndPoints.establishments());
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),400);
        Assert.assertEquals(jsonobj.get("message").toString(),"Invalid or missing parameter \"city_id\"");
    }

    @Test
    public void testGetEstablishmentsWithLonOnly(){
        double longitude = 80.2707;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("lon",longitude).get(EndPoints.establishments());
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertEquals(resp.getStatusCode(),400);
        Assert.assertEquals(jsonobj.get("message").toString(),"Invalid or missing parameter \"city_id\"");
    }

    @Test
    public void testGetEstablishmentsWithLatLonOnly(){
        double latitude = 13.0827;
        double longitude = 80.2707;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("lat",latitude).
                        queryParam("lon",longitude).get(EndPoints.establishments());
        Assert.assertEquals(resp.getStatusCode(),200);
    }

    @Test
    public void testGetEstablishmentsWithLatLonCityOfSameRegion(){
        double latitude = 13.0827;
        double longitude = 80.2707;
        int city_id = 7;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("city_id",city_id).queryParam("lat",latitude).
                        queryParam("lon",longitude).get(EndPoints.establishments());
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertNotNull(jsonobj.get("establishments"),"No establishments in this region, please check.");
        Assert.assertEquals(resp.getStatusCode(),200);
    }

    @Test
    public void testGetEstablishmentsWithLatLonCityOfDifferentRegion(){
        double latitude = 12.9716;
        double longitude = 77.5946;
        int city_id = 7;
        Response resp = RestAssured.given()
                .spec(EndPoints.getBaseRequestSpecification()).queryParam("city_id",city_id).queryParam("lat",latitude).
                        queryParam("lon",longitude).get(EndPoints.establishments());
        JSONObject jsonobj = new JSONObject(resp.body().asString());
        Assert.assertNotNull(jsonobj.get("establishments"),"No establishments in this region, please check.");
        Assert.assertEquals(resp.getStatusCode(),200);

    }

}
