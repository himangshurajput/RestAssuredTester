package com.lazarus.go4testing;

import static io.restassured.RestAssured.*;

import com.lazarus.go4testing.model.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Collection;

@RunWith(Parameterized.class)
public class testHomeAndSearchPage {

    @Parameterized.Parameters
    public static Collection data() {
        return AuthRetriever.getUserList();
    }

    @Parameterized.Parameter(0)
    public String username;

    @Parameterized.Parameter(1)
    public String password;

    @Before
    public void setup() {
        RestAssured.baseURI = AuthRetriever.getBaseUrl();
        RestAssured.oauth2(AuthRetriever.getAuthToken(new User(username, password)));
    }

    @Test
    public void testHome(){
        Response response = RestAssured.get(baseURI + "/go4apis/home");
        Assert.assertEquals(200, response.getStatusCode());
        String blockCount = response.jsonPath().getString("block_count");
        Assert.assertTrue("Block count for home page is zero", Integer.parseInt(blockCount) > 0);
    }

    @Test
    public void testSearch() throws Exception{
        File file = new File("src/test/resources/search.json");
        Response response = given().body(file).with().contentType(ContentType.JSON)
                .then().expect()
                .when()
                .post(baseURI + "/go4apis/search");
        Assert.assertEquals(200, response.getStatusCode());
        String responseCount = response.jsonPath().getString("totalResponses");
        Assert.assertTrue("Response count for search page is zero", Integer.parseInt(responseCount) > 0);
    }
}
