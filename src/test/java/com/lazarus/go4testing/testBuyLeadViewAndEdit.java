package com.lazarus.go4testing;


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
import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.*;


@RunWith(Parameterized.class)
public class testBuyLeadViewAndEdit {

    String buyLeadId = AuthRetriever.getBuyLeadId();

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
        RestAssured.baseURI = AuthRetriever.getBaseUrl() + "/go4apis/buylead";
        RestAssured.oauth2(AuthRetriever.getAuthToken(new User(username,password)));
    }



    @Test
    public void viewBuyLead(){
        Response response = RestAssured.get("/" + buyLeadId);
        Assert.assertEquals("Status Code Check Failed", 200, response.getStatusCode());
        String pageName = response.jsonPath().getString("page");
        Assert.assertEquals("Page name is not matched", pageName, "buylead");
        String blocks = response.jsonPath().getString("blocks.size()");
        Assert.assertTrue("There are zero blocks", Integer.parseInt(blocks) > 0);
        String blockCount = response.jsonPath().getString("block_count");
        Assert.assertTrue("Block Count is zero", Integer.parseInt(blockCount) > 0);
    }

    @Test
    public void getNewBuyLeadForm(){
        Response response = RestAssured.get("/new/new/0");
        Assert.assertEquals("Status Code Check Failed", 200, response.getStatusCode());
        Assert.assertEquals("Form Name Check Failed", "Buylead",response.jsonPath().getString("formName"));
        Assert.assertEquals(response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[0].fields.key"), "[compName]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[1].fields.key"), "[product]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[2].fields.key"), "[reqDetails]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[0].fields.key"), "[name]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[1].fields.key"), "[address]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[2].fields.key"), "[city, zip]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[3].fields.key"), "[state]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[4].fields.key"), "[country, phone]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[5].fields.key"), "[email]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[6].fields.key"), "[homepage]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[7].fields.key"), "[preferredRegions]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[8].fields.key"), "[excludedRegions]");
    }


    @Test
    public void getRepostBuyLeadForm(){
        Response response = RestAssured.get("/new/repost/" + buyLeadId);
        Assert.assertEquals("Status Code Check Failed", 200, response.getStatusCode());
        Assert.assertEquals("Form Name Check Failed", "Buylead",response.jsonPath().getString("formName"));
        Assert.assertEquals(response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[0].fields.key"), "[compName]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[1].fields.key"), "[product]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[2].fields.key"), "[reqDetails]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[0].fields.key"), "[name]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[1].fields.key"), "[address]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[2].fields.key"), "[city, zip]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[3].fields.key"), "[state]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[4].fields.key"), "[country, phone]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[5].fields.key"), "[email]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[6].fields.key"), "[homepage]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[7].fields.key"), "[preferredRegions]");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[8].fields.key"), "[excludedRegions]");
    }

    @Test
    public void postRepostBuyLeadWithNullValues(){
        File file = new File("src/test/resources/repostBuyLeadWithNullValues.json");
        Response response = given().body(file).with().contentType(ContentType.JSON)
                .then().expect()
                .when()
                .post(baseURI + "/post/repost/" + buyLeadId);
        Assert.assertEquals("Status Code Check Failed", 601, response.getStatusCode());
        Assert.assertEquals("Form Name Mismatched", "Buylead", response.jsonPath().getString("formName"));
        Assert.assertEquals(response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[0].fields[0].validationMessage"), "must not be blank");
        Assert.assertEquals(response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[1].fields[0].validationMessage"), "must not be blank");
        Assert.assertEquals(response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[2].fields[0].validationMessage"), "must not be blank");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[0].fields[0].validationMessage"), "must not be blank");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[1].fields[0].validationMessage"), "must not be blank");
        Assert.assertEquals(response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[2].fields[0].validationMessage"), "must not be blank");
    }


}
