package com.bestbuy.functions;

import io.restassured.response.Response;
import util.RandomDataGenerator;

import static io.restassured.RestAssured.given;


public class ProductAPIFunctions extends BaseAPIFunctions {

    public void deleteProduct(String productID) {
        given()
                .contentType("application/json")
                .when()
                .delete("/products/" + productID)
                .then()
                .assertThat()
                .statusCode(200);
    }

    public Response getProduct(String productID, int expectedResponseCode) {
        return given()
                .get("products/" + productID)
                .then()
                .assertThat()
                .statusCode(expectedResponseCode)
                .extract()
                .response();
    }

    public Response createProduct(String postProductData) {
        return given()
                .contentType("application/json")
                .body(postProductData)
                .when()
                .post("/products")
                .then()
                .assertThat()
                .extract()
                .response();
    }

    public Response updateProduct(String postProductData, String productID) {
        return given()
                .contentType("application/json")
                .body(postProductData)
                .when()
                .patch("/products/" + productID)
                .then()
                .assertThat()
                .extract()
                .response();
    }

    public String generateProductBody() {
        int randomNumber = RandomDataGenerator.generateRandomNumber();
        String name = "TestProduct_" + randomNumber;
        String type = "TestType";
        int price = 85;
        String description = "Test Product Description";
        String model = "TestModel";
        return "{" +
                "\"name\": \"" + name + "\"," +
                "\"type\": \"" + type + "\"," +
                "\"upc\": \"" + randomNumber + "\"," +
                "\"price\": " + price + "," +
                "\"description\": \"" + description + "\"," +
                "\"model\": \"" + model + "\"" +
                "}";
    }

}
