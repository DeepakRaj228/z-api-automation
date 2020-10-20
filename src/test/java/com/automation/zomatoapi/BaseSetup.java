package com.automation.zomatoapi;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseSetup {

    static RequestSpecification getBaseRequestSpecification(){
        String auth_key = "yxxyxyxxx";
        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("https://developers.zomato.com/api/v2.1");
        requestBuilder.addHeader("user-key", auth_key);

        return requestBuilder.build();
    }

}


