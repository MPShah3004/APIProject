package com.bestbuy.api;

import com.bestbuy.functions.StoreAPIFunctions;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class StoreAPITest extends BaseAPITest {

    private StoreAPIFunctions storeAPIFunctions = new StoreAPIFunctions();

    @Test
    public void
    validate_store_schema() {
        storeAPIFunctions.validateJsonSchema("stores", "schema/Store.json");
    }

    @Test
    public void
    create_store() {
        String postStoreData = storeAPIFunctions.generateStoreBody();
        Response response = storeAPIFunctions.createStore(postStoreData);
        String storeID = response.path("id").toString();
        assertThat("Store is not created", storeID != null);
    }

    @Test
    public void
    get_selected_store() {
        String postStoreData = storeAPIFunctions.generateStoreBody();
        Response response = storeAPIFunctions.createStore(postStoreData);
        String storeID = response.path("id").toString();
        response = storeAPIFunctions.getStore(storeID, 200);
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void
    get_stores_with_selected_service() {
        String searchCriteria = "?service.name=Apple Shop";
        Response response = storeAPIFunctions.getStore(searchCriteria, 200);
        assertThat(response, is(notNullValue()));
        List<String> listOfStoresWithService = response.path("data.services.name");
        assertThat("Did not have results", listOfStoresWithService.size() > 0);
    }

    @Test
    public void
    show_error_on_invalid_store() {
        String storeID = "-2112212";
        Response response = storeAPIFunctions.getStore(storeID, 404);
        assertThat(response.path("code"), is(404));
        assertThat(response.path("message"), is("No record found for id '" + storeID + "'"));
    }

    @Test
    public void
    update_store() {
        String postProductData = storeAPIFunctions.generateStoreBody();
        Response response = storeAPIFunctions.createStore(postProductData);
        String storeID = response.path("id").toString();
        String jsonResponseAfterUpdate = response.asString().replace("BigBox", "SmallBox");
        response = storeAPIFunctions.updateStore(jsonResponseAfterUpdate, storeID);
        assertThat("Store details not updated", response.path("type").equals("SmallBox"));
    }

    @Test
    public void
    delete_store() {
        String postProductData = storeAPIFunctions.generateStoreBody();
        Response response = storeAPIFunctions.createStore(postProductData);
        String storeID = response.path("id").toString();
        storeAPIFunctions.deleteStore(storeID);
        storeAPIFunctions.getStore(storeID, 404);
    }
}
