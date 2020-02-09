package com.automation.tests.Homework;

import org.junit.jupiter.api.BeforeAll;

import com.automation.utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;

public class Homework3 {

    @BeforeAll
    public void setup(){
        baseURI=ConfigurationReader.getProperty("harryPotter_uri");
    }

        /*
        Verify sorting hat  1. Send a get request to /sortingHat. Request includes :
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that response body contains one of the following houses:
        " Gryﬃndor", "Ravenclaw", "Slytherin", "Huﬄepuﬀ"
    Verify bad key  1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value invalid
    2. Verify status code 401, content type application/json; charset=utf-8
    3. Verify response status line include message Unauthorized
    4. Verify that response body says "error": "API Key Not Found"
    Verify no key  1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    2. Verify status code 409, content type application/json; charset=utf-8
    3. Verify response status line include message Conﬂict
    4. Verify that response body says "error": "Must pass API key for request"

    Verify number of characters  1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify response contains 194 characters

    Verify number of character id and house  1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify all characters in the response have id ﬁeld which is not empty
    4. Verify that value type of the ﬁeld dumbledoresArmy is a boolean in all characters in the response
    5. Verify value of the house in all characters in the response is one of the following:
        " Gryﬃndor", "Ravenclaw", "Slytherin", "Huﬄepuﬀ"
    Verify all character information 1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Select name of any random character
    4. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Query param name with value from step 3
    5. Verify that response contains the same character information from step 3. Compare all ﬁelds.
    Verify name search 1. Send a get request to /characters. Request includes :
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

    Verify house members 1. Send a get request to /houses. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Capture the id of the Gryﬃndor house
    4. Capture the ids of the all members of the Gryﬃndor house
    5. Send a get request to /houses/:id. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Path param id with value from step 3
    6. Verify that response contains the  same member ids as the step 4

    Verify house members again 1. Send a get request to /houses/:id. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Path param id with value 5a05e2b252f721a3cf2ea33f
    2. Capture the ids of all members
    3. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Query param house with value Gryﬃndor
    4. Verify that response contains the same member ids from step 2
    Verify house with most members 1. Send a get request to /houses. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that Gryﬃndor house has the most member
         */
}
