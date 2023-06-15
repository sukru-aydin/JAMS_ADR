package com.jams.utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class APIUtils {

    public static Response sendGetRequest(String url){
        return RestAssured.given().accept(ContentType.JSON)
                .when().get(url);
    }

    public static Response sendGetRequestWithBasicAuth(String url, String username, String password){
        return RestAssured.given().accept(ContentType.JSON)
                .and().auth().basic(username,password)
                .when().get(url);
    }


    public static void compareLists(List<String> list1, List<String> list2) {
        List<String> differingObjects_List1 = new ArrayList<>();
        List<String> differingObjects_List2 = new ArrayList<>();

        for (String obj : list1) {
            if (!list2.contains(obj)) {
                differingObjects_List1.add(obj);
            }
        }

        for (String obj : list2) {
            if (!list1.contains(obj)) {
                differingObjects_List2.add(obj);
            }
        }

        if (differingObjects_List1.isEmpty() && differingObjects_List2.isEmpty()) {
            System.out.println("The lists are identical.");
        } else {
            System.out.println("Differing objects:");
            System.out.println("differingObjects_List1 = " + differingObjects_List1);
            System.out.println("differingObjects_List2 = " + differingObjects_List2);
        }
    }


    public static List<String> convertIntegerListToStringList(List<Integer> integerList) {
        List<String> stringList = new ArrayList<>();

        for (Integer num : integerList) {
            stringList.add(String.valueOf(num));
        }

        return stringList;
    }


}
