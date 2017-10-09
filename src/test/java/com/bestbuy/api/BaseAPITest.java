package com.bestbuy.api;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class BaseAPITest {

    @BeforeClass
    public static void setBaseURL() {
        RestAssured.baseURI = "http://localhost:3030";
    }
}
