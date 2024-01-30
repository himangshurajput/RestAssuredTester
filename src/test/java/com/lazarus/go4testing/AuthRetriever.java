package com.lazarus.go4testing;

import com.lazarus.go4testing.model.User;
import io.restassured.response.Response;
import lombok.Data;
import lombok.Getter;
import org.junit.Test;
import org.springframework.security.core.parameters.P;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;


public class AuthRetriever {

    private final static  String baseUrl = "https://devapi.go4worldbusiness.com:9292";

    private final static String authUrl = baseUrl + "/oauth/token";

    private static String buyLeadId = "1505726342";

    private static String type = "";
    private static String authToken;

    private static String username = "";

    private static String lastLogin = "";


    public static String getAuthToken(User user) {
        if(user.getUsername() == null || user.getUsername().isEmpty()){
            username = "";
            lastLogin = "";
            authToken = "";
            type = "";
            return "";
        }
        else if(!lastLogin.equals(user.getUsername())){
            Response response = given()
                    .contentType("application/json").body(user)
                    .when()
                    .post(authUrl)
                    .then()
                    .extract().response();
            authToken = response.jsonPath().getString("accessToken");
            lastLogin = user.getUsername();
            if(user.getUsername().equals("sunandan2") || user.getUsername().equals("shivam0002")){
                username = user.getUsername();
                type = "NOT GOLD";
            }
            else{
                type = "GOLD";
            }
        }
        return authToken;
    }


    public static List<Object> getUserList(){
        return Arrays.asList(new Object[][]{
                {"sunandan2", "m2"} , {"USDSuccess2", "USDSuccess2"}, {"", ""}
        });
    }

    public static String getType(){
        return type;
    }

    public static String getBaseUrl(){
        return baseUrl;
    }

    public static String getMemberId(){
            return "1296463";
    }

    public static String getProductId(){
            return "932801";
    }

    public static String getUsername(){
        return username;
    }
    public static String getBuyLeadId(){
        return buyLeadId;
    }


//    public static String getAuthToken() {
////        System.out.println("Inside auth token");
////        authToken = given()
////                .contentType("application/json").body(new User("m2","m2"))
////                .when()
////                .post(authUrl)
////                .then()
////                .extract().response().getBody().jsonPath().getString("token");
////        return authToken;
//        return "";
//    }

}
