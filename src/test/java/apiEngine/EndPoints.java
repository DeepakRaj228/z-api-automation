package apiEngine;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class EndPoints {

    private static final String BASE_URL = "https://developers.zomato.com/api/v2.1";

    public static RequestSpecification getBaseRequestSpecification(){
        String auth_key = "AAABBBCCCCC";
        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri(BASE_URL);
        requestBuilder.addHeader("user-key", auth_key);

        return requestBuilder.build();
    }

    public static String categories(){
        return "/categories";
    }

    public static String cuisines(){
        return "/cuisines";
    }

    public static String dailymenu(){
        return "/dailymenu";
    }

    public static String establishments(){
        return "/establishments";
    }

    public static String restaurant(){
        return "/restaurant";
    }
}
