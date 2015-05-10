package com.copyflynx.database;

import java.util.ArrayList;

/**
 * Created by harneev on 5/10/2015.
 */
public class DataList {

    private static ArrayList<String> sUrls = new ArrayList<String>();
    private static ArrayList<String> sTitles = new ArrayList<String>();

    public static void insertData(String url, String title) {
        sUrls.add(url);
        sTitles.add(title);
    }

    public static ArrayList<String> getURL() {
        return sUrls;
    }

    public static ArrayList<String> getTitles() {
        return sTitles;
    }
}
