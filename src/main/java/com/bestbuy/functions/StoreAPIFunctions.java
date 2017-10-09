package com.bestbuy.functions;

import io.restassured.response.Response;
import util.RandomDataGenerator;

import static io.restassured.RestAssured.given;


public class StoreAPIFunctions extends BaseAPIFunctions {

    public void deleteStore(String storeID) {
        given()
                .contentType("application/json")
                .when()
                .delete("/stores/" + storeID)
                .then()
                .assertThat()
                .statusCode(200);
    }

    public Response getStore(String searchCriteria, int expectedResponseCode) {
        return given()
                .get("stores/" + searchCriteria)
                .then()
                .assertThat()
                .statusCode(expectedResponseCode)
                .extract()
                .response();
    }

    public Response createStore(String postStoreData) {
        return given()
                .contentType("application/json")
                .body(postStoreData)
                .when()
                .post("/stores")
                .then()
                .assertThat()
                .extract()
                .response();
    }

    public Response updateStore(String postStoreData, String storeID) {
        return given()
                .contentType("application/json")
                .body(postStoreData)
                .when()
                .patch("/stores/" + storeID)
                .then()
                .assertThat()
                .extract()
                .response();
    }

    public String generateStoreBody() {
        int randomNumber = RandomDataGenerator.generateRandomNumber();
        return "{" +
                "\"name\": \"Rest Store_" + randomNumber + "\"," +
                "\"type\": \"BigBox\"," +
                "\"address\": \"123 Fake St\"," +
                "\"address2\": \"\"," +
                "\"city\": \"Springfield\"," +
                "\"state\": \"MN\"," +
                "\"zip\": \"55123\"," +
                "\"lat\": 44.969658," +
                "\"lng\": -93.449539," +
                "\"hours\": \"Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8\"" +
                "}";
    }

}
