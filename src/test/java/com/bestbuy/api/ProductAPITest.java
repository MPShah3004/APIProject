package com.bestbuy.api;

import com.bestbuy.functions.ProductAPIFunctions;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class ProductAPITest extends BaseAPITest {

    private ProductAPIFunctions productAPIFunctions = new ProductAPIFunctions();

    @Test
    public void
    validate_product_schema() {
        productAPIFunctions.validateJsonSchema("products", "schema/Product.json");
    }

    @Test
    public void
    create_product() {
        String postProductData = productAPIFunctions.generateProductBody();
        Response response = productAPIFunctions.createProduct(postProductData);
        String productID = response.path("id").toString();
        assertThat("Product is not created", productID != null);
    }

    @Test
    public void
    get_selected_product() {
        String postProductData = productAPIFunctions.generateProductBody();
        Response response = productAPIFunctions.createProduct(postProductData);
        String productID = response.path("id").toString();
        response = productAPIFunctions.getProduct(productID, 200);
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void
    show_error_on_invalid_product() {
        String productID = "-2112212";
        Response response = productAPIFunctions.getProduct(productID, 404);
        assertThat(response.path("code"), is(404));
        assertThat(response.path("message"), is("No record found for id '" + productID + "'"));
    }

    @Test
    public void
    update_product() {
        String postProductData = productAPIFunctions.generateProductBody();
        Response response = productAPIFunctions.createProduct(postProductData);
        String productID = response.path("id").toString();
        String jsonResponseAfterUpdate = response.asString().replace("TestModel", "ModelUpdated");
        response = productAPIFunctions.updateProduct(jsonResponseAfterUpdate, productID);
        assertThat("Product details not updated", response.path("model").equals("ModelUpdated"));
    }

    @Test
    public void
    delete_product() {
        String postProductData = productAPIFunctions.generateProductBody();
        Response response = productAPIFunctions.createProduct(postProductData);
        String productID = response.path("id").toString();
        productAPIFunctions.deleteProduct(productID);
        productAPIFunctions.getProduct(productID, 404);
    }
}
