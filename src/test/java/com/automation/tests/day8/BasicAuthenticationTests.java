package com.automation.tests.day8;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class BasicAuthenticationTests {

    @BeforeAll
    public static void setup(){
        //https will not work, because of SSL certificate issues
        //this website doesn't have it
        baseURI="http://practice.cybertekschool.com";
    }

    @Test
    @DisplayName("basic authentication test")
    public void test1(){
        given().
                auth().basic("admin", "admin").
                when().
                get("/basic_auth").prettyPeek().prettyPeek().
                then().assertThat().statusCode(200);
    }
}
