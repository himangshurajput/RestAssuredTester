package com.lazarus.go4testing;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazarus.go4testing.model.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.core.AnyOf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
@RunWith(Parameterized.class)
public class testMemberView {

     final static String memberId = AuthRetriever.getMemberId();

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
        RestAssured.baseURI = AuthRetriever.getBaseUrl() + "/go4apis/member/" + memberId;
        RestAssured.oauth2(AuthRetriever.getAuthToken(new User(username, password)));
    }

    @Test
    public void getMemberViewPage(){
        Response response = RestAssured.get("/");
        Assert.assertEquals("Status Code Check Failed", 200, response.getStatusCode());
        Assert.assertTrue("Number of page blocks should be greater than zero", Integer.parseInt(response.jsonPath().getString("page_blocks.size()")) > 0);
        Assert.assertTrue("Tab size should be greater than zero", Integer.parseInt(response.jsonPath().getString("tabs.size()")) > 0);
        Assert.assertEquals("member_view", response.jsonPath().getString("page"));
        Assert.assertTrue("Membership type flag is not correct", response.jsonPath().getString("membership_type").equals("0") || response.jsonPath().getString("membership_type").equals("1"));
        Assert.assertNotEquals("Country code is null",null, response.jsonPath().getString("country.code"));
        Assert.assertNotEquals("Country flag is null",null, response.jsonPath().getString("country.flag"));
        Assert.assertNotEquals("Country name is null",null, response.jsonPath().getString("country.name"));
        Assert.assertEquals("Rate Member action is not present", "rate_member", response.jsonPath().getString("page_blocks[4].content[0].action"));
        Assert.assertEquals("File complaint action is not present", "file_complaint", response.jsonPath().getString("page_blocks[4].content[1].action"));
        Assert.assertEquals("Share link is not working", 200, when().get(response.jsonPath().getString("share_link")).getStatusCode());
        if(response.jsonPath().getString("logo_url") != null || !response.jsonPath().getString("logo_url").isEmpty())
        {
            Assert.assertEquals("LogoUrl link is not working", 200, when().get(response.jsonPath().getString("logo_url")).getStatusCode());
        }
        Assert.assertTrue("Rating format is wrong",Pattern.matches("^\\d+(\\.\\d+)?$",response.jsonPath().getString("rating")));
        if(response.jsonPath().getString("tabs.size()").equals("2")){
            checkTabsForNotGold(response);
        }
        else {
            checkTabsForGold(response);
        }
        if(response.jsonPath().getString("banner_images.size()") != null)
        {
            for(int i = 0;i < Integer.parseInt(response.jsonPath().getString("banner_images.size()"));i++){
                Assert.assertEquals("Link for banner image no " + i + "is not working", 200, when().get(response.jsonPath().getString("banner_images[" + i + "].url")).getStatusCode());
            }
        }
    }

    @Test
    public void getMemberViewProductsPage(){
        Response response = RestAssured.get("/products");
        Assert.assertEquals("Status Code Check Failed", 200, response.getStatusCode());
        Assert.assertTrue("Tab size should be greater than zero", Integer.parseInt(response.jsonPath().getString("tabs.size()")) > 0);
        Assert.assertEquals("Page name is not correct", "member_product_view", response.jsonPath().getString("page"));
        Assert.assertTrue("Number of page blocks is zero", Integer.parseInt(response.jsonPath().getString("page_blocks.size()")) > 0);
        Assert.assertNotEquals("Country code is null",null, response.jsonPath().getString("country.code"));
        Assert.assertNotEquals("Country flag is null",null, response.jsonPath().getString("country.flag"));
        Assert.assertNotEquals("Country name is null",null, response.jsonPath().getString("country.name"));
        Assert.assertEquals("Share link is not working", 200, when().get(response.jsonPath().getString("share_link")).getStatusCode());
        if(response.jsonPath().getString("logo_url") != null || !response.jsonPath().getString("logo_url").isEmpty())
        {
            Assert.assertEquals("LogoUrl link is not working", 200, when().get(response.jsonPath().getString("logo_url")).getStatusCode());
        }
        Assert.assertTrue("Rating format is wrong",Pattern.matches("^\\d+(\\.\\d+)?$",response.jsonPath().getString("rating")));
        if(response.jsonPath().getString("tabs.size()").equals("2")){
            checkTabsForNotGold(response);
        }
        else {
            checkTabsForGold(response);
        }
    }

    @Test
    public void getMemberViewManagementPage(){
        Response response = RestAssured.get("/management");
        Assert.assertEquals("Status Code Check Failed", 200, response.getStatusCode());
        Assert.assertTrue("Tab size should be greater than zero", Integer.parseInt(response.jsonPath().getString("tabs.size()")) > 0);
        Assert.assertEquals("Page name is not correct", "member_management_view", response.jsonPath().getString("page"));
        Assert.assertTrue("Number of page blocks is zero", Integer.parseInt(response.jsonPath().getString("page_blocks.size()")) > 0);
        Assert.assertNotEquals("Country code is null","null", response.jsonPath().getString("country.code"));
        Assert.assertNotEquals("Country flag is null","null", response.jsonPath().getString("country.flag"));
        Assert.assertNotEquals("Country name is null","null", response.jsonPath().getString("country.name"));
        Assert.assertEquals("Rate Member action is not present", "rate_member", response.jsonPath().getString("page_blocks[5].content[0].action"));
        Assert.assertEquals("File complaint action is not present", "file_complaint", response.jsonPath().getString("page_blocks[5].content[1].action"));
        Assert.assertEquals("Share link is not working", 200, when().get(response.jsonPath().getString("share_link")).getStatusCode());
        if(response.jsonPath().getString("logo_url") != null || !response.jsonPath().getString("logo_url").isEmpty())
        {
            Assert.assertEquals("LogoUrl link is not working", 200, when().get(response.jsonPath().getString("logo_url")).getStatusCode());
        }
        Assert.assertTrue("Rating format is wrong",Pattern.matches("^\\d+(\\.\\d+)?$",response.jsonPath().getString("rating")));
        if(response.jsonPath().getString("tabs.size()").equals("2")){
            checkTabsForNotGold(response);
        }
        else {
            checkTabsForGold(response);
        }
    }

    @Test
    public void getMemberViewFacilitiesPage(){
        Response response = RestAssured.get("/facilities");
        Assert.assertEquals("Status Code Check Failed", 200, response.getStatusCode());
        Assert.assertTrue("Tab size should be greater than zero", Integer.parseInt(response.jsonPath().getString("tabs.size()")) > 0);
        Assert.assertEquals("Page name is not correct", "member_facilities_view", response.jsonPath().getString("page"));
        Assert.assertTrue("Number of page blocks is zero", Integer.parseInt(response.jsonPath().getString("page_blocks.size()")) > 0);
        Assert.assertNotEquals("Country code is null","null", response.jsonPath().getString("country.code"));
        Assert.assertNotEquals("Country flag is null","null", response.jsonPath().getString("country.flag"));
        Assert.assertNotEquals("Country name is null","null", response.jsonPath().getString("country.name"));
        Assert.assertEquals("Rate Member action is not present", "rate_member", response.jsonPath().getString("page_blocks[5].content[0].action"));
        Assert.assertEquals("File complaint action is not present", "file_complaint", response.jsonPath().getString("page_blocks[5].content[1].action"));
        Assert.assertEquals("Share link is not working", 200, when().get(response.jsonPath().getString("share_link")).getStatusCode());
        if(response.jsonPath().getString("logo_url") != null || !response.jsonPath().getString("logo_url").isEmpty())
        {
            Assert.assertEquals("LogoUrl link is not working", 200, when().get(response.jsonPath().getString("logo_url")).getStatusCode());
        }
        Assert.assertTrue("Rating format is wrong",Pattern.matches("^\\d+(\\.\\d+)?$",response.jsonPath().getString("rating")));
        if(response.jsonPath().getString("tabs.size()").equals("2")){
            checkTabsForNotGold(response);
        }
        else {
            checkTabsForGold(response);
        }

    }

    @Test
    public void getMemberViewNewsroomPage(){
        Response response = RestAssured.get("/newsroom");
        Assert.assertEquals("Status Code Check Failed", 200, response.getStatusCode());
        Assert.assertTrue("Tab size should be greater than zero", Integer.parseInt(response.jsonPath().getString("tabs.size()")) > 0);
        Assert.assertEquals("Page name is not correct", "member_newsroom_view", response.jsonPath().getString("page"));
        Assert.assertTrue("Number of page blocks is zero", Integer.parseInt(response.jsonPath().getString("page_blocks.size()")) > 0);
        Assert.assertNotEquals("Country code is null","null", response.jsonPath().getString("country.code"));
        Assert.assertNotEquals("Country flag is null","null", response.jsonPath().getString("country.flag"));
        Assert.assertNotEquals("Country name is null","null", response.jsonPath().getString("country.name"));
        Assert.assertEquals("Rate Member action is not present", "rate_member", response.jsonPath().getString("page_blocks[5].content[0].action"));
        Assert.assertEquals("File complaint action is not present", "file_complaint", response.jsonPath().getString("page_blocks[5].content[1].action"));
        Assert.assertEquals("Share link is not working", 200, when().get(response.jsonPath().getString("share_link")).getStatusCode());
        if(response.jsonPath().getString("logo_url") != null || !response.jsonPath().getString("logo_url").isEmpty())
        {
            Assert.assertEquals("LogoUrl link is not working", 200, when().get(response.jsonPath().getString("logo_url")).getStatusCode());
        }
        Assert.assertTrue("Rating format is wrong",Pattern.matches("^\\d+(\\.\\d+)?$",response.jsonPath().getString("rating")));
        if(response.jsonPath().getString("tabs.size()").equals("2")){
            checkTabsForNotGold(response);
        }
        else {
            checkTabsForGold(response);
        }

    }


    public static void checkTabsForGold(Response response){
        Assert.assertEquals("Tab 0 member Id mismatch", memberId,response.jsonPath().getString("tabs[0].member_id"));
        Assert.assertEquals("Tab 0 action is not correct", "member_view",response.jsonPath().getString("tabs[0].action"));
        Assert.assertEquals("Tab 0 action type is not correct", "page",response.jsonPath().getString("tabs[0].action_type"));
        Assert.assertEquals("Tab 0 title is not correct", "Company",response.jsonPath().getString("tabs[0].title"));
        Assert.assertEquals("Tab 1 member Id mismatch", memberId,response.jsonPath().getString("tabs[1].member_id"));
        Assert.assertEquals("Tab 1 action is not correct", "member_product_view",response.jsonPath().getString("tabs[1].action"));
        Assert.assertEquals("Tab 1 action type is not correct", "page",response.jsonPath().getString("tabs[1].action_type"));
        Assert.assertEquals("Tab 1 title is not correct", "Products",response.jsonPath().getString("tabs[1].title"));
        Assert.assertEquals("Tab 2 member Id mismatch", memberId,response.jsonPath().getString("tabs[2].member_id"));
        Assert.assertEquals("Tab 2 action is not correct", "member_management",response.jsonPath().getString("tabs[2].action"));
        Assert.assertEquals("Tab 2 action type is not correct", "page",response.jsonPath().getString("tabs[2].action_type"));
        Assert.assertEquals("Tab 2 title is not correct", "Management",response.jsonPath().getString("tabs[2].title"));
        Assert.assertEquals("Tab 3 member Id mismatch", memberId,response.jsonPath().getString("tabs[3].member_id"));
        Assert.assertEquals("Tab 3 action is not correct", "member_facilities",response.jsonPath().getString("tabs[3].action"));
        Assert.assertEquals("Tab 3 action type is not correct", "page",response.jsonPath().getString("tabs[3].action_type"));
        Assert.assertEquals("Tab 3 title is not correct", "Facilities",response.jsonPath().getString("tabs[3].title"));
        Assert.assertEquals("Tab 4 member Id mismatch", memberId,response.jsonPath().getString("tabs[4].member_id"));
        Assert.assertEquals("Tab 4 action is not correct", "member_newsroom",response.jsonPath().getString("tabs[4].action"));
        Assert.assertEquals("Tab 4 action type is not correct", "page",response.jsonPath().getString("tabs[4].action_type"));
        Assert.assertEquals("Tab 4 title is not correct", "NewsRoom",response.jsonPath().getString("tabs[4].title"));
    }

    public static void checkTabsForNotGold(Response response){
        Assert.assertEquals("Tab 0 member Id mismatch", memberId,response.jsonPath().getString("tabs[0].member_id"));
        Assert.assertEquals("Tab 0 action is not correct", "member_view",response.jsonPath().getString("tabs[0].action"));
        Assert.assertEquals("Tab 0 action type is not correct", "page",response.jsonPath().getString("tabs[0].action_type"));
        Assert.assertEquals("Tab 0 title is not correct", "Company",response.jsonPath().getString("tabs[0].title"));
        Assert.assertEquals("Tab 1 member Id mismatch", memberId,response.jsonPath().getString("tabs[1].member_id"));
        Assert.assertEquals("Tab 1 action is not correct", "member_product_view",response.jsonPath().getString("tabs[1].action"));
        Assert.assertEquals("Tab 1 action type is not correct", "page",response.jsonPath().getString("tabs[1].action_type"));
        Assert.assertEquals("Tab 1 title is not correct", "Products",response.jsonPath().getString("tabs[1].title"));
    }


}
