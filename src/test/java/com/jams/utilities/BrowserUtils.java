package com.jams.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class BrowserUtils {

    //waits for given duration time
    public static void sleep(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String readJsonFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        BufferedReader reader = null;
        StringBuilder jsonContent = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return jsonContent.toString();
    }


}
