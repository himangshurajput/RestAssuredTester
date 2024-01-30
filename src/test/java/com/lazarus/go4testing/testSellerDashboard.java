package com.lazarus.go4testing;


import com.lazarus.go4testing.model.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.*;
@RunWith(Parameterized.class)
public class testSellerDashboard {

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
        RestAssured.baseURI = AuthRetriever.getBaseUrl() + "/go4apis/seller-dashboard";
        RestAssured.authentication = oauth2(AuthRetriever.getAuthToken(new User(username, password)));
    }

    @Test
    public void getMatchingBuyLeadsPage(){
        Response response = RestAssured.get("/");
        Assert.assertEquals("Status Code Check Failed", 200, response.getStatusCode());
        if(response.jsonPath().getString("isLoggedIn") != null && response.jsonPath().getString("isLoggedIn").equals("true")){
            Assert.assertTrue("Number of blocks is zero", Integer.parseInt(response.jsonPath().getString("blocks.size()")) > 0);
            Assert.assertTrue("Tabs Count is zero", Integer.parseInt(response.jsonPath().getString("tabs.size()")) > 0);
            Assert.assertEquals("Page Name Mismatched", "matching-buyleads", response.jsonPath().getString("page"));
            Assert.assertTrue("Block Count is zero", Integer.parseInt(response.jsonPath().getString("block_count")) > 0);
            for(int i = 0;i < Integer.parseInt(response.jsonPath().getString("tabs.size()"));i++){
                Assert.assertEquals("Action Type of Tab " + i + "is not correct", "page",response.jsonPath().getString("tabs[" + i + "].action_type"));
                if(i == 0){
                    Assert.assertEquals(" Value of action of Tab 0 is not : matching_buyleads","matching_buyleads",response.jsonPath().getString("tabs[" + i + "].action"));
                    Assert.assertEquals(" Value of action of Tab 0 is not : Business Inquiries","Business Inquiries",response.jsonPath().getString("tabs[" + i + "].title"));
                }
                else if(i == 1){
                    Assert.assertEquals(" Value of action of Tab 1 is not : membership","membership",response.jsonPath().getString("tabs[" + i + "].action"));
                    Assert.assertEquals(" Value of action of Tab 1 is not : Your  Membership","Your  Membership",response.jsonPath().getString("tabs[" + i + "].title"));
                }
                else if(i == 2){
                    Assert.assertEquals(" Value of action of Tab 2 is not : edit_profile","edit_profile",response.jsonPath().getString("tabs[" + i + "].action"));
                    Assert.assertEquals(" Value of action of Tab 2 is not : Edit Profile","Edit Profile",response.jsonPath().getString("tabs[" + i + "].title"));
                }
                else if(i == 3){
                    Assert.assertEquals(" Value of action of Tab 3 is not : spam","spam",response.jsonPath().getString("tabs[" + i + "].action"));
                    Assert.assertEquals(" Value of action of Tab 3 is not : Getting too many emails?","Getting too many emails?",response.jsonPath().getString("tabs[" + i + "].title"));
                }
            }
            Assert.assertEquals("Logged in flag is false", "true",response.jsonPath().getString("isLoggedIn"));
        }
        else {
            response.jsonPath().getString("blockContent[0].isLoggedIn");
            Assert.assertEquals("Logged in flag is true", "false",response.jsonPath().getString("blockContent[0].isLoggedIn"));
        }
    }

    @Test
    public void getMemberShipDetailsPage(){
        Response response = RestAssured.get("/edit-profile/membership");
        System.out.println(response.jsonPath().getString("isLoggedIn"));
        if(response.jsonPath().getString("blockContent[0].isLoggedIn") == null) {
            Assert.assertEquals("Status Code Check Failed", 200, response.statusCode());
            Assert.assertTrue("Number of blocks is zero", Integer.parseInt(response.jsonPath().getString("blocks.size()")) > 0);
            Assert.assertEquals("Page Name Mismatched", "membership-status", response.jsonPath().getString("page"));
            Assert.assertTrue("Block Count is zero", Integer.parseInt(response.jsonPath().getString("blockCount")) > 0);
            for (int i = 0; i < Integer.parseInt(response.jsonPath().getString("blocks.size()")); i++) {
                if (response.jsonPath().getString("blocks[" + i + "].type").equals("link")) {
                    System.out.println(response.jsonPath().getString("blocks[" + i + "].contentBlock[0]"));
                }
            }
        }
        else{
            Assert.assertEquals("Status Code Check Failed", 403, response.statusCode());
            Assert.assertEquals("Logged in flag is true", "false",response.jsonPath().getString("blockContent[0].isLoggedIn"));
        }
    }

    @Test
    public void getEditCompanyForm(){
        Response response = RestAssured.get("/edit-profile/company");
        if(response.jsonPath().getString("blockContent[0].isLoggedIn") == null) {
            Assert.assertEquals("Status Code Check Failed", 200, response.getStatusCode());
            Assert.assertEquals("Form Name Mismatched", "Company Details", response.jsonPath().getString("formName"));
            Assert.assertEquals("Form Label Mismatch", "Company Details", response.jsonPath().getString("formTitle.label"));
            Assert.assertEquals("supplierOf", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[0].fields[0].key"));
            Assert.assertEquals("buyerOf", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[1].fields[0].key"));
            Assert.assertEquals("tradeServices", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[2].fields[0].key"));
            Assert.assertEquals("primaryBusiness", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[3].fields[0].key"));
            Assert.assertEquals("yearEstablished", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[3].fields[1].key"));
            Assert.assertEquals("annualSales", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[4].fields[0].key"));
            Assert.assertEquals("certifications", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[4].fields[1].key"));
            Assert.assertEquals("name", response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[0].fields[0].key"));
            Assert.assertEquals("designation", response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[0].fields[1].key"));
            Assert.assertEquals("address", response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[1].fields[0].key"));
            Assert.assertEquals("city", response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[2].fields[0].key"));
            Assert.assertEquals("zip", response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[2].fields[1].key"));
            Assert.assertEquals("state", response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[3].fields[0].key"));
            Assert.assertEquals("country", response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[3].fields[1].key"));
            Assert.assertEquals("phone", response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[4].fields[0].key"));
            Assert.assertEquals("email", response.jsonPath().getString("formGroup[1].groupTitle.fieldGroup[5].fields[0].key"));
        }
        else {
            Assert.assertEquals("Status Code Check Failed", 403, response.getStatusCode());
            Assert.assertEquals("Logged in flag is true", "false",response.jsonPath().getString("blockContent[0].isLoggedIn"));
        }
    }

    @Test
    public void getEditHomeForm(){
        Response response = RestAssured.get("/edit-profile/homepage");
        if(response.jsonPath().getString("blockContent[0].isLoggedIn") == null) {
            Assert.assertEquals("Status Code Check Failed", 200, response.getStatusCode());
            if(AuthRetriever.getType() == null || AuthRetriever.getType().equals("GOLD")){
                System.out.println(response.jsonPath().getString("formName"));
                Assert.assertEquals("Form Name Mismatched", "Homepage", response.jsonPath().getString("formName"));
                Assert.assertEquals("Form Label Mismatch", "Details About Your Company", response.jsonPath().getString("formTitle.label"));
                Assert.assertEquals("logoUrl", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[0].fields[0].key"));
                Assert.assertEquals("memo1", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[1].fields[0].key"));
                Assert.assertEquals("image1", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[2].fields[0].key"));
                Assert.assertEquals("memo2", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[3].fields[0].key"));
                Assert.assertEquals("image2", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[4].fields[0].key"));
                Assert.assertEquals("memo3", response.jsonPath().getString("formGroup[0].groupTitle.fieldGroup[5].fields[0].key"));
            }
        }
        else {
            Assert.assertEquals("Status Code Check Failed", 403, response.getStatusCode());
            Assert.assertEquals("Logged in flag is true", "false",response.jsonPath().getString("blockContent[0].isLoggedIn"));
        }
    }
}
