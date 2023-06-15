package com.jams.step_defs;
import com.jams.utilities.APIUtils;
import com.jams.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.*;


public class Step_Defs_Api {


    List<String> listID_searchG2 = new ArrayList<>();
    List<String> listID_live = new ArrayList<>();

    @Given("User gets searchG2 neutral ids as List")
    public void user_gets_search_g2_neutral_ids_as_list() {

        String url = ConfigurationReader.getProperty("searchG2_url");

        Response response = APIUtils.sendGetRequest(url);
        //response.prettyPrint();

        JsonPath jsonPath = response.jsonPath();
        listID_searchG2 = jsonPath.getList("response.docs.id");

    }

    @Given("User gets live neutral ids as List")
    public void user_gets_live_neutral_ids_as_list() {

        String url = ConfigurationReader.getProperty("live_url");
        String username = ConfigurationReader.getProperty("live_username");
        String password = ConfigurationReader.getProperty("live_password");

        Response response = APIUtils.sendGetRequestWithBasicAuth(url,username,password);
        //response.prettyPrint();

        JsonPath jsonPath = response.jsonPath();

        List<Integer> listID_live_Integer = jsonPath.getList("Id");
        listID_live = APIUtils.convertIntegerListToStringList(listID_live_Integer);
    }


    @Then("User compares {int} lists")
    public void user_compares_lists(Integer int1) {
        System.out.println(listID_searchG2.size());
        System.out.println(listID_live.size());

        //listID_searchG2.add("1");
        //listID_live.add("2");

        Collections.sort(listID_searchG2);
        Collections.sort(listID_live);

        System.out.println(listID_searchG2);
        System.out.println(listID_live);

        APIUtils.compareLists(listID_searchG2,listID_live);


    }




}
