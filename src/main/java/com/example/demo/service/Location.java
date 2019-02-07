package com.example.demo.service;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
public class Location {
    @Autowired(required = true)
    RestTemplate restTemplate;
    public String countryUrl="https://jck-extranet.axisrooms.com/api/be/getAllLocation?countryId=1";
    Map<Long,String> stateMap=new HashMap<>();
    final static org.apache.log4j.Logger log = Logger.getLogger(Location.class);

    public String getState(String state) {
        Long stateId = 0l;
        try{
        if (stateMap.isEmpty()) {
            String RESPONSE_DATA = getRestTemplate(countryUrl);
            JSONObject obj = new JSONObject(RESPONSE_DATA);
            JSONArray arr = obj.getJSONArray("locations");
            for (int i = 0; i < arr.length(); i++) {
                stateMap.put(arr.getJSONObject(i).getLong("stateId"), arr.getJSONObject(i).getString("state").toUpperCase());
            }
        }
        for (Map.Entry<Long, String> entry : stateMap.entrySet()) {
            if (entry.getValue().equals(state.toUpperCase())) {
                stateId=entry.getKey();
            }
        }
         return stateId.toString();
    }catch (NullPointerException ex){
        log.info(ex.toString());
        return "0";
        }
    }
    public String getCity(String city,String stateId) {
        Long cityId = 0l;
        try {
            Map<Long, String> cityMap = new HashMap<>();
            if(isAlpha(city)) {
                String stateUrl = "https://jck-extranet.axisrooms.com/api/be/getAllLocation?stateId=" + Integer.parseInt(stateId) + "";
                String RESPONSE_DATA = getRestTemplate(stateUrl);
                JSONObject obj = new JSONObject(RESPONSE_DATA);
                JSONArray arr = obj.getJSONArray("locations");
                for (int i = 0; i < arr.length(); i++) {
                    cityMap.put(arr.getJSONObject(i).getLong("cityId"), arr.getJSONObject(i).getString("city").toUpperCase());
                    // System.out.println(cityMap);
                }
                for (Map.Entry<Long, String> entry : cityMap.entrySet()) {
                    if (entry.getValue().equals(city.toUpperCase())) {
                        cityId = entry.getKey();
                    }
                }
                return cityId.toString();
            }else return "0";
        }catch (NullPointerException ex){
            log.info(ex.toString());
            return "0";
        }catch (Exception e) {
            log.info(e.toString());
            return "0";
        }

    }
    public String getLocations(String location,String cityId) {
        try{
        Map<Long,String> locationMap=new HashMap<>();
        String cityUrl="https://jck-extranet.axisrooms.com/api/be/getAllLocation?cityId="+Integer.parseInt(cityId)+"";
        String RESPONSE_DATA = getRestTemplate(cityUrl);
        JSONObject obj = new JSONObject(RESPONSE_DATA);
        JSONArray arr = obj.getJSONArray("locations");

        Long locationId=0l;
        for (int i = 0; i < arr.length(); i++)
        {
            locationMap.put(arr.getJSONObject(i).getLong("locationId"),arr.getJSONObject(i).getString("location").toUpperCase());
        }
        for (Map.Entry<Long, String> entry : locationMap.entrySet()) {
            if (entry.getValue().equals(location.toUpperCase())) {
                locationId=entry.getKey();
            }
        }
        return locationId.toString();
        }catch (NullPointerException ex) {
            log.info(ex.toString());
            return "0";
        }catch (Exception e){
            log.info(e.toString());
            return "0";
        }
    }

    private String getRestTemplate(String URL) {
        String exceptionMessage="";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("apikey", "6ac26a4cb838a51fb5406b724ba2c43c4110f133");
            headers.add("channelid", "9");
            headers.add("access_key", "c6533c7cf985b3e4f6f84eaa5ac54a263ba9f1fdb4bd0f915d891d33e5b4b64e");
            HttpEntity request = new HttpEntity<>(headers);
            ResponseEntity<String> responseData = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
            return responseData.getBody();
        } catch (Exception ex) {
            exceptionMessage=ex.getMessage();
            log.info(ex.toString());
            return "{\"Error\":{\"ErrorCode\":\"ER500\",\"ErrorMessage\":\""+exceptionMessage+"\"}}";
        }
    }
    public boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}

