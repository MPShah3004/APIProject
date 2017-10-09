package com.bestbuy.functions;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BaseAPIFunctions {
    public void validateJsonSchema(String endPoint, String jsonSchemaFile) {
        given()
                .get("/" + endPoint)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath(jsonSchemaFile));
    }

}
