package com.automation.tests.Homework;

import com.automation.utilities.APIUtilities;
import com.automation.utilities.ConfigurationReader;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class Homework1_UINames {

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
        given()
               .get()
        .then().assertThat().statusCode(200).
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
                queryParam("gender", "male").      // queryParam("gender", "female").
                get().
        then().assertThat().
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
                get().
         then().assertThat().
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
                get().
        then().
               assertThat().
                       statusCode(400).
                       contentType("application/json; charset=utf-8").
                       statusLine(containsString("Bad Request")).
                       body("error", containsString("Invalid gender")).
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
                get().
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
        Response response =
                given().queryParam("region","Turkey")
                .queryParam("amount", 5)
                .get();
        response.then().assertThat().
                        statusCode(200).
                        contentType("application/json; charset=utf-8").
                        log().all(true);

      List<Object> list = response.jsonPath().get();
        assertFalse(APIUtilities.hasDuplicates(list), "List have duplicates");

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
                get().
        then().assertThat()
                        .statusCode(200)
                        .contentType("application/json; charset=utf-8")
                        .body("region", everyItem(is("Turkey")))
                        .body("gender", everyItem(is("female")))
                        .body("", hasSize(3))
                        .log().body(true);
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
            get().
     then().assertThat()
            .statusCode(200)
            .contentType("application/json; charset=utf-8")
            .body("",hasSize(2))
            .log().all(true);


    }
}

