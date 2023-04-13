package com.isa.nbpapi_spring.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiNbpService {

    private static final String URL = "http://api.nbp.pl/api/exchangerates/tables/A/";

    public List<Map<String, Object>> getExchangeRates() {
        try {
            String json = getJsonFromUrl(URL);
            JSONArray jsonArray = new JSONArray(json);
            JSONArray ratesArray = jsonArray.getJSONObject(0).getJSONArray("rates");

            List<Map<String, Object>> ratesList = new ArrayList<>();
            for (int i = 0; i < ratesArray.length(); i++) {
                JSONObject rate = ratesArray.getJSONObject(i);
                Map<String, Object> rateMap = new HashMap<>();
                rateMap.put("currency", rate.getString("currency"));
                rateMap.put("code", rate.getString("code"));
                rateMap.put("mid", rate.getDouble("mid"));
                ratesList.add(rateMap);
            }

            return ratesList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private String getJsonFromUrl(String url) {
        String json = "";
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                json += line;
            }

            reader.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}
