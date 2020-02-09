package com.automation.tests.Homework;

import com.automation.pojos.Spartan;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;

import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class Homework2 {

    @BeforeAll
    public static void setup(){
        baseURI= ConfigurationReader.getProperty("github_uri");
    }
        /*
    1. Send a get request to /orgs/:org. Request includes :
    • Path param org with value cucumber
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify value of the login ﬁeld is cucumber
    4. Verify value of the name ﬁeld is cucumber
    5. Verify value of the id ﬁeld is 320565
         */
        @Test
        @DisplayName("Verify organization information")
    public void organizationInformation(){
            given().
                    accept(ContentType.JSON).
                    get("/orgs/Cucumber").prettyPeek().
                    then().assertThat().statusCode(200).
                    contentType("application/json; charset=utf-8").
                    body("login", is("cucumber")).
                    body("name", is("Cucumber")).
                    body("id", is(320565));
        }



        /*
    1. Send a get request to /orgs/:org. Request includes :
    • Header Accept with value application/xml
    • Path param org with value cucumber
    2. Verify status code 415, content type application/json; charset=utf-8
    3. Verify response status line include message Unsupported Media Type
         */
    @Test
    @DisplayName("Verify error message")
    public void ErrorMessage() {
        given().accept("application/xml").
                get("/orgs/Cucumber").prettyPeek().
                then().assertThat().statusCode(415).
                contentType("application/json; charset=utf-8").
                statusLine(containsString("Unsupported Media Type"));
    }


    /*
         1. Send a get request to /orgs/:org. Request includes :
        • Path param org with value cucumber
        2. Grab the value of the ﬁeld public_repos
        3. Send a get request to /orgs/:org/repos. Request includes :
        • Path param org with value cucumber
        4. Verify that number of objects in the response  is equal to value from step 2
     */
    @Test
    @DisplayName("Verify number of repositories")
    public void numberOfRepositories(){
       Response response = given().accept(ContentType.JSON).get("/orgs/Cucumber").prettyPeek();
             int repNumber =response.jsonPath().getInt("public_repos");

        Response response2 = given().accept(ContentType.JSON).get("/orgs/Cucumber/repos");

        List<Object> number = response2.jsonPath().get();

        assertEquals(repNumber,number.size());
    }

    /*
        1. Send a get request to /orgs/:org/repos. Request includes :
        • Path param org with value cucumber
        2. Verify that id ﬁeld is unique in every in every object in the response
        3. Verify that node_id ﬁeld is unique in every in every object in the response
     */
    @Test
    @DisplayName("Verify repository id information")  //?????????
    public void idInformation(){
        Response response=given().accept(ContentType.JSON).
                get("/orgs/Cucumber/repos").prettyPeek();

        List<Integer> id = response.jsonPath().getList("id");
        for(int i=0; i<id.size(); i++){
            for(int j=0; i<id.size(); j++){
                if(id.get(i).equals(id.get(j))){

                }
            }
        }
        List<Integer> nodeId = response.jsonPath().getList("node_id");
        for(int i=0; i<nodeId.size(); i++){
            for(int j=0; i<nodeId.size(); j++){
                if(id.get(i).equals(nodeId.get(j))){

                }
            }
        }


    }

    /*
    1. Send a get request to /orgs/:org. Request includes :
    • Path param org with value cucumber
    2. Grab the value of the ﬁeld id
    3. Send a get request to /orgs/:org/repos. Request includes :
    • Path param org with value cucumber
    4. Verify that value of the id inside the owner object in every response is equal to value from step 2
     */
    @Test
    @DisplayName("Verify that repository owner information")
    public void RepositoryOwnerInformation(){
        Response response = given().accept(ContentType.JSON).
                get("/orgs/Cucumber").prettyPeek();
               int valueOfId= response.jsonPath().getInt("id");
        System.out.println(valueOfId);

         Response response2 = given().accept(ContentType.JSON).
                 get("/orgs/Cucumber/repos");
                // get("/orgs/Cucumber/repos").prettyPeek();
         List<Integer> valueOfId2 = response2.jsonPath().getList("owner.id");

        for (int each: valueOfId2){
            assertEquals(valueOfId, each);
        }

    }



    /*
    Ascending order by full_name sort
    1. Send a get request to /orgs/:org/repos. Request includes :
    • Path param org with value cucumber
    • Query param sort with value full_name
    2. Verify that all repositories are listed in alphabetical order based on the value of the ﬁeld name
     */
    @Test
    @DisplayName("Verify that ascending order by full_name sort")   // ??????
    public void AscendingOrderByFullNameSort(){
        Response response=given().accept(ContentType.JSON).
                queryParam("sort","full_name").
                get("/orgs/Cucumber/repos").prettyPeek();

                List<String> names = response.jsonPath().getList("name");
                for (int i=0; i<names.size()-1; i++){
                 //   assertTrue(names.get(i));
                }
    }



            /*
        1. Send a get request to /orgs/:org/repos. Request includes :
        • Path param org with value cucumber
        • Query param sort with value full_name
        • Query param direction with value desc
        2. Verify that all repositories are listed in reverser alphabetical order based on the value of the ﬁeld name
             */
        @Test
        @DisplayName("Verify that descending order by full_name sort")  // ???????
        public void DescendingOrderByFullNameSort(){
          Response response=  given().accept(ContentType.JSON).
                    queryParam("sort","full_name").
                    get("/orgs/Cucumber/repos").prettyPeek();

            List<String> names = response.jsonPath().getList("name");
            for(int i =0; i<names.size()-1; i++){
                //assertTrue();
            }
        }



            /*
        Default sort
        1. Send a get request to /orgs/:org/repos. Request includes :
        • Path param org with value cucumber
        2. Verify that by default all repositories are listed in descending order based on the value of the ﬁeld created_at
         */
        @Test
        public void DefaultSort(){
            Response response=  given().accept(ContentType.JSON).
                    queryParam("sort","full_name").
                    get("/orgs/Cucumber/repos").prettyPeek();

            List<String> names = response.jsonPath().getList("name");
            for(int i =0; i<names.size()-1; i++){
                //assertTrue();
            }
          }

}






