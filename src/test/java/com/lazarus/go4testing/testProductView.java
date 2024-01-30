package com.lazarus.go4testing;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazarus.go4testing.model.User;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
//@RunWith(Parameterized.class)
public class testProductView {

    String productId = AuthRetriever.getProductId();


    @Parameterized.Parameters
    public static Collection data() {
        return AuthRetriever.getUserList();
    }

    @Parameterized.Parameter(0)
    public String username;

    @Parameterized.Parameter(1)
    public String password;


    @Before
    public void setup(){
        RestAssured.baseURI = AuthRetriever.getBaseUrl() + "/go4apis/product";
        RestAssured.oauth2(AuthRetriever.getAuthToken(new User(username,password)));
    }



    @Test
    public void getProductView(){
        Response response = RestAssured.get("/" + productId);
        Assert.assertTrue("Status Code Check Failed", (response.statusCode() == HttpStatus.SC_OK) || (response.statusCode() == HttpStatus.SC_NO_CONTENT));
        JsonPath jsonPath = response.jsonPath();
        String pageBlocksCount = jsonPath.getString("page_blocks.size()");
        Assert.assertTrue("Number of pageblocks should be greater than zero", Integer.parseInt(pageBlocksCount) > 0);
        String tabSize = response.jsonPath().getString("tabs.size()");
        Assert.assertTrue("Tabsize should be greater than zero", Integer.parseInt(tabSize) > 0);
        String pageName = response.jsonPath().getString("page");
        Assert.assertEquals(pageName, "product_view");
    }

    public void addProduct(){

    }
























//    @Test
//    public void MemberEdit() throws JsonProcessingException {
//        String jsonValue = given()
//                .when()
//                .get(baseURI + "/edit-profile/company")
//                .body()
//                .asString();
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> jsonMap = mapper.readValue(jsonValue, Map.class);
//        System.out.println(jsonMap.get("formTitle"));
//        System.out.println(jsonMap.get("formGroup"));
//
//    }


}
