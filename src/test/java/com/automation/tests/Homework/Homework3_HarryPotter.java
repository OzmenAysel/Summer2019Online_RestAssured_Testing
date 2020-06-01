package com.automation.tests.Homework;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;

import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/*
In this assignment, you will test Happy Potter API created based on Harry Potter movie series.
Full documentation for Happy Potter API can be found here: https://www.potterapi.com/.
Watch all the movies or read all the books to better understanding of this API (this is optional).
To test this API, you have to register and get access key. Sign up for access key. Access key is required for most of the requests.
Import the Postman collection using this link: https://www.getpostman.com/collections/ 5ceaa3e2705188383075
In postman create an environment for this API.

Add the following variables:
1. Variable name: baseURL Variable value: https://www.potterapi.com/v1/  (Links to an external site.)
2. Variable name: apiKey Variable value: your api key from the https://www.potterapi.com/ (Links to an external site.)
Automate the given test cases.
You can use any existing project. You can automate all test cases in same class or different classes.
For verifying all of the use pojos. Create pojo classes for Character and House in pojos package based on the provided json files.
 */
public class Homework3_HarryPotter {

    @BeforeAll
    public static void setup(){
        baseURI=ConfigurationReader.getProperty("harryPotter_uri");
    }


    /*
    1. Send a get request to /sortingHat
    Request includes :
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that response body contains one of the following houses:
        " Gryﬃndor", "Ravenclaw", "Slytherin", "Huﬄepuﬀ"
    */
    @Test
    @DisplayName("Verify sorting hat")
    public void test1(){
        List<String> list = new ArrayList<>(Arrays.asList("\"Gryﬃndor\"", "\"Ravenclaw\"", "\"Slytherin\"", "\"Huﬄepuﬀ\""));
        Response response =
                when().get("/sortingHat").prettyPeek();

      //  System.out.println(response.body().asString());

        assertEquals(200, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());
        assertTrue(list.contains(response.body().asString()));

    }


    /*
     1. Send a get request to /characters
     Request includes :
    • Header Accept with value application/json
    • Query param key with value invalid
    2. Verify status code 401, content type application/json; charset=utf-8
    3. Verify response status line include message Unauthorized
    4. Verify that response body says "error": "API Key Not Found"
     */
    @Test
    @DisplayName("Verify unauthorized status code")
    public void test2(){
        String expected = "{\"error\":\"API Key Not Found\"}";
        given().accept("application/json")
                .queryParam("key", "invalid")
                .when()
                .get("/characters")
                .then()
                .assertThat()
                        .statusCode(401)
                        .contentType("application/json; charset=utf-8" )
                        .statusLine(containsString("Unauthorized"))
                        .body(containsString(expected));

    }


    /*
    1.Send a get request to /characters
      Request includes :
    • Header Accept with value application/json
    2. Verify status code 409, content type application/json; charset=utf-8
    3. Verify response status line include message Conﬂict
    4. Verify that response body says "error": "Must pass API key for request"
     */
    @Test
    @DisplayName("Verify no key")
    public void test3(){
        String expected ="{\"error\":\"Must pass API key for request\"}";
        given().accept(ContentType.JSON)
                .when()
                .get("/characters").prettyPeek()
        .then()
                .assertThat().statusCode(409)
                .contentType("application/json; charset=utf-8")
                .statusLine(containsString("Conflict"))
                .body(containsString(expected));
    }

        /*
    Verify number of characters
    1. Send a get request to /characters
    Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify response contains 194 characters
    */
     @Test
    @DisplayName("Verify number of characters")
    public void test4(){
             given()
                 .accept("application/json")
                 .queryParam("key", ConfigurationReader.getProperty("harryPotterApiKey"))
                 .when()
                 .get("/characters")
                 .then().assertThat().statusCode(200)
                 .contentType("application/json; charset=utf-8")
                 .body("",hasSize(195));

     }

    /*
    Verify number of character id and house
    1. Send a get request to /characters
     Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify all characters in the response have id ﬁeld which is not empty
    4. Verify that value type of the ﬁeld dumbledoresArmy is a boolean in all characters in the response
    5. Verify value of the house in all characters in the response is one of the following:
        " Gryﬃndor", "Ravenclaw", "Slytherin", "Huﬄepuﬀ"
     */
    @Test
    @DisplayName("Verify number of character id and house")
    public void test5(){
        List<String> expected = new ArrayList<>(Arrays.asList("\"Gryﬃndor\"", "\"Ravenclaw\"", "\"Slytherin\"", "\"Huﬄepuﬀ\""));
        Response response = given().accept("application/json")
                .queryParam("key", ConfigurationReader.getProperty("harryPotterApiKey"))
                .when()
                .get("/characters");

        List<String> listID = response.jsonPath().getList("_id");
        List<Boolean> dumble = response.jsonPath().getList("dumbledoresArmy");
        List<String> house = response.jsonPath().getList("house");

        System.out.println(listID);
        System.out.println(dumble);
        System.out.println(house);

                response.then().assertThat()
                        .statusCode(200)
                        .contentType("application/json; charset=utf-8")
                        .body("_id", everyItem(notNullValue()))
                        .body("dumbledoresArmy", everyItem(is(oneOf(true, false))))
                        .body("house", everyItem(is(oneOf("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff", null))));

    }
    /*
    Verify all character information
    1. Send a get request to /characters.
    Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Select name of any random character
    4. Send a get request to /characters.
    Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Query param name with value from step 3
    5. Verify that response contains the same character information from step 3. Compare all ﬁelds.
     */
    @Test
    @DisplayName("Verify all character information")
    public void test6(){
       Response response1 = given()
                .accept("application/json")
                .queryParam("key",ConfigurationReader.getProperty("harryPotterApiKey") )
                .when()
                .get("/characters");

       Map<String, ?> character1 = response1.jsonPath().getMap("[18]");
       String firstName =(String) character1.get("name");
       System.out.println("character1 = " + character1);

       response1.then().assertThat()
               .statusCode(200)
               .contentType("application/json; charset=utf-8");


        Response response2 = given()
                .accept("application/json")
                .queryParam("key",ConfigurationReader.getProperty("harryPotterApiKey") )
                .queryParam("name", firstName)
                .when()
                .get("/characters");


        Map<String, ?> character2 = response2.jsonPath().getMap("[0]");
        System.out.println(character2);
        assertEquals(character1, character2);

    }


    /*
    1. Send a get request to /characters.
     Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Query param name with value Harry Potter
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify name Harry Potter
    4. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Query param name with value Marry Potter
    5. Verify status code 200, content type application/json; charset=utf-8
    6. Verify response body is empty
     */
    @Test
    @DisplayName("Verify name search")
    public void test7(){
        Response response1 =given()
                .accept("application/json")
                .queryParam("key", ConfigurationReader.getProperty("harryPotterApiKey"))
                .queryParam("name", "Harry Potter")
                .when()
                .get("/characters");
                response1.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8")
                                    .body("name[0]", containsString("Harry Potter") );

        Response response2 = given()
                .accept("application/json")
                .queryParam("key", ConfigurationReader.getProperty("harryPotterApiKey"))
                .queryParam("name", "Marry Potter")
                .when()
                .get("/characters").prettyPeek();
        response2.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8")
                .body("",is(empty()) );
    }

    /*
        1. Send a get request to /houses.
        Request includes :
        • Header Accept with value application/json
        • Query param key with value {{apiKey}}
        2. Verify status code 200, content type application/json; charset=utf-8
        3. Capture the id of the Gryﬃndor house
        4. Capture the ids of the all members of the Gryﬃndor house
        5. Send a get request to /houses/:id.
            Request includes :
        • Header Accept with value application/json
        • Query param key with value {{apiKey}}
        • Path param id with value from step 3
        6.Verify that response contains the  same member ids as the step 4
     */
    @Test
    @DisplayName("Verify house members")
    public void test8(){
        Response response1 = given()
                .accept("application/json")
                .queryParam("key", ConfigurationReader.getProperty("harryPotterApiKey"))
                .when()
                .get("/houses");
        response1.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");

        String gryffindorID = response1.jsonPath().getString("find{it.name==\"Gryffindor\"}._id");
        System.out.println("gryffindorID: "+gryffindorID);

        List<List<String>> memberIDs1 = response1.jsonPath().getList("find{it.name==\"Gryffindor\"}.members");
        System.out.println("memberIDs1: " +memberIDs1);



                Response response2 =
                        given()
                        .accept("application/json")
                        .queryParam("key", ConfigurationReader.getProperty("harryPotterApiKey"))
                        .pathParam("id", gryffindorID)
                        .when()
                        .get("/houses/{id}");

        List<String> memberIDs2 = response2.jsonPath().getList("members._id");
        System.out.println("memberIDs2 = " + memberIDs2);

        assertEquals(memberIDs1, memberIDs2.get(0));

    }


    /*
    1. Send a get request to /houses/:id.
     Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Path param id with value 5a05e2b252f721a3cf2ea33f
    2. Capture the ids of all members
    3. Send a get request to /characters.
     Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Query param house with value Gryﬃndor
    4. Verify that response contains the same member ids from step 2
    Verify house with most members
    1. Send a get request to /houses.
    Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that Gryﬃndor house has the most member
         */
    @Test
    @DisplayName("Verify house members again")
    public void test9(){
        Response response1 = given()
                .accept("application/json")
                .queryParam("key", ConfigurationReader.getProperty("harryPotterApiKey") )
                .pathParam("id", "5a05e2b252f721a3cf2ea33f")
                .when()
                .get("/houses/{id}");

            List<String> membersID = response1.jsonPath().getList("members._id");
            System.out.println("membersID: "+membersID);

        Response response2 = given()
                .accept("application/json")
                .queryParam("key", ConfigurationReader.getProperty("harryPotterApiKey") )
                .queryParam("house", "Gryffindor")
                .when()
                .get("/characters");

        List<String> ID = response1.jsonPath().getList("_id");
         System.out.println("ID: "+ID);

        // assertTrue(ID.contains(membersID));  ??????

         Response response3 = given()
                 .accept("application/json")
                 .queryParam("key", ConfigurationReader.getProperty("harryPotterApiKey") )
                 .when()
                 .get("/houses");
                 response3.then().assertThat().statusCode(200).contentType("application/json; charset=utf-8");









    }
}
