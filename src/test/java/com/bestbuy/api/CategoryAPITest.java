package com.bestbuy.api;

import com.bestbuy.functions.CategoryAPIFunctions;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class CategoryAPITest extends BaseAPITest {

    private CategoryAPIFunctions categoryAPIFunctions = new CategoryAPIFunctions();

    @Test
    public void
    validate_category_schema() {
        categoryAPIFunctions.validateJsonSchema("categories", "schema/Categories.json");
    }

    @Test
    public void
    create_category() {
        String postStoreData = categoryAPIFunctions.generateCategoryBody();
        Response response = categoryAPIFunctions.createCategory(postStoreData);
        String categoryID = response.path("id").toString();
        assertThat("Category is not created", categoryID != null);
    }

    @Test
    public void
    get_selected_category() {
        String postStoreData = categoryAPIFunctions.generateCategoryBody();
        Response response = categoryAPIFunctions.createCategory(postStoreData);
        String categoryID = response.path("id").toString();
        response = categoryAPIFunctions.getCategory(categoryID, 200);
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void
    get_category_with_selected_criteria() {
        String searchCriteria = "?name[$like]=*TV*";
        Response response = categoryAPIFunctions.getCategory(searchCriteria, 200);
        assertThat(response, is(notNullValue()));
        List<String> listOfCategories = response.path("data.subCategories.name");
        assertThat("Did not have results", listOfCategories.size() > 0);
    }

    @Test
    public void
    show_error_on_invalid_category() {
        String categoryID = "-2112212";
        Response response = categoryAPIFunctions.getCategory(categoryID, 404);
        assertThat(response.path("code"), is(404));
        assertThat(response.path("message"), is("No record found for id '" + categoryID + "'"));
    }

    @Test
    public void
    update_category() {
        String postCategoryData = categoryAPIFunctions.generateCategoryBody();
        Response response = categoryAPIFunctions.createCategory(postCategoryData);
        String categoryID = response.path("id").toString();
        String jsonResponseAfterUpdate = response.asString().replace("New Category", "Updated Category");
        response = categoryAPIFunctions.updateCategory(jsonResponseAfterUpdate, categoryID);
        assertThat("Store details not updated", response.path("name").equals("Updated Category"));
    }

    @Test
    public void
    delete_category() {
        String postCategoryData = categoryAPIFunctions.generateCategoryBody();
        Response response = categoryAPIFunctions.createCategory(postCategoryData);
        String categoryID = response.path("id").toString();
        categoryAPIFunctions.deleteCategory(categoryID);
        categoryAPIFunctions.getCategory(categoryID, 404);
    }
}
