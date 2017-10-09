package com.bestbuy.functions;

import io.restassured.response.Response;
import util.RandomDataGenerator;

import static io.restassured.RestAssured.given;


public class CategoryAPIFunctions extends BaseAPIFunctions {

    public void deleteCategory(String categoryID) {
        given()
                .contentType("application/json")
                .when()
                .delete("/categories/" + categoryID)
                .then()
                .assertThat()
                .statusCode(200);
    }

    public Response getCategory(String searchCriteria, int expectedResponseCode) {
        return given()
                .get("/categories/" + searchCriteria)
                .then()
                .assertThat()
                .statusCode(expectedResponseCode)
                .extract()
                .response();
    }

    public Response createCategory(String postCategoryData) {
        return given()
                .contentType("application/json")
                .body(postCategoryData)
                .when()
                .post("/categories")
                .then()
                .assertThat()
                .extract()
                .response();
    }

    public Response updateCategory(String postCategoryData, String categoryID) {
        return given()
                .contentType("application/json")
                .body(postCategoryData)
                .when()
                .patch("/categories/" + categoryID)
                .then()
                .assertThat()
                .extract()
                .response();
    }

    public String generateCategoryBody() {
        int randomNumber = RandomDataGenerator.generateRandomNumber();
        return "{" +
                "\"id\": \"RestCategory_" + randomNumber + "\"," +
                "\"name\": \"New Category\"" +
                "}";
    }

}
