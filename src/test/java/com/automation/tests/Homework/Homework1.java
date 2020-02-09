package com.automation.tests.Homework;

import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Homework1 {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("uinames_uri");
    }

    /*
No params test
1. Send a get request without providing any parameters
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that name, surname, gender, region ﬁelds have value
     */
    @Test
    public void NoParamsTest(){
        given().
                accept("application/json; charset=utf-8").
                get("/api/").
        then().
                assertThat().statusCode(200).
                        and().contentType("application/json; charset=utf-8").
                        and().body("name", is(notNullValue())).
                        and().body("surname", is(notNullValue())).
                        and().body("gender", is(notNullValue())).
                        and().body("region", is(notNullValue())).
                        log().all(true);
    }

    /*
    Gender test
    1. Create a request by providing query parameter: gender, male or female
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that value of gender ﬁeld is same from step 1
     */
    @Test
    public void genderTest(){
        given().
                accept("application/json; charset=utf-8").
                queryParam("gender", "male").      // queryParam("gender", "female").
                get("/api/").
        then().
                assertThat().
                        statusCode(200).
                        contentType("application/json; charset=utf-8").
                        body("gender", is("male")).
                        log().all(true);
    }

    /*
    2 params test
    1. Create a request by providing query parameters: a valid region and gender
    NOTE: Available region values are given in the documentation
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that value of gender ﬁeld is same from step 1
    4. Verify that value of region ﬁeld is same from step
         */
    @Test
    public void twoParamsTest(){
        given().
                queryParam("region", "Turkey" ).
                queryParam("gender", "female").
                get("/api/").
         then().
                assertThat().
                        statusCode(200).
                        contentType("application/json; charset=utf-8").
                        body("region", is("Turkey")).
                        body("gender", is("female")).
                        log().all(true);
    }


    /*
    Invalid gender test
    1. Create a request by providing query parameter: invalid gender
    2. Verify status code 400 and status line contains Bad Request
    3. Verify that value of error ﬁeld is Invalid gender
     */
    @Test
    public void invalidGenderTest(){
        given().
                queryParam("gender", "invalid"). //????
                get("/api/").
        then().
               assertThat().
                       statusCode(400).
                       contentType("application/json; charset=utf-8").
                       statusLine(containsString("Bad Request")).
                       body("error", is("Invalid gender")).
                       log().all(true);
    }


        /*
        Invalid region test
    1. Create a request by providing query parameter: invalid region
    2. Verify status code 400 and status line contains Bad Request
    3. Verify that value of error ﬁeld is Region or language not found
         */
    @Test
    public void  invalidRegionTest (){
        given().
                queryParam("region", "invalid").
                get("/api/").
         then().
                assertThat().statusCode(400).
                        contentType("application/json; charset=utf-8").
                        statusLine(containsString("Bad Request")).   //  statusLine("HTTP/1.1 400 Bad Request").//???????
                        body("error",is("Region or language not found")).
                        log().all(true);
    }


        /*
        Amount and regions test
    1. Create request by providing query parameters: a valid region and amount (must be bigger than 1)
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that all objects have diﬀerent name+surname combination
         */
    @Test
    public void  amountAndRegionsTest(){
        given().
                queryParam("region","Turkey").
                queryParam("amount", 5).
                get("/api/").
        then().
                assertThat().
                        statusCode(200).
                        contentType("application/json; charset=utf-8").
                        // body
                        log().all(true);

        //  body("title", hasItems("Turkey", "5"));


//      List<String> list = response.thenReturn().jsonPath().get();
//        System.out.println(list);
//        for(int i =0; i<list.size(); i++){
//       }


    }


        /*
        3 params test
    1. Create a request by providing query parameters: a valid region, gender and amount (must be bigger than 1)
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that all objects the response have the same region and gender passed in step 1
         */
    @Test
    public void threeParamsTest(){
        given().
                queryParam("region", "Turkey").
                queryParam("gender", "female").
                queryParam("amount", "3").
                get("/api/").
        then().
                assertThat().
                        statusCode(200).
                        contentType("application/json; charset=utf-8").
                        body("region", hasItem("Turkey")).
                        body("gender", hasItem("female")).
                        body("", hasSize(3)).   // ???????
                        log().body(true);
    }


        /*
        Amount count test
    1. Create a request by providing query parameter: amount (must be bigger than 1)
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that number of objects returned in the response is same as the amount passed in step 1
         */
    @Test
    public void amountCountTest(){
    given().
            queryParam("amount", "2").
            get("api/").
     then().
            assertThat().statusCode(200).
            contentType("application/json; charset=utf-8").
            body("",hasSize(2)).
            log().all(true);


    }
}

