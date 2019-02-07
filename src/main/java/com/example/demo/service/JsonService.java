package com.example.demo.service;

import com.example.demo.ServerResponse.Error;
import com.example.demo.ServerResponse.Response;
import com.example.demo.generatedclasses.common.WrapperClasses.*;
import com.example.demo.generatedclasses.common.bean.scrunch.*;
import com.example.demo.staticdump.model.document.StaticDumpHotel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.json.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JsonService {
    @Autowired(required = true)
    RestTemplate restTemplate;

    @Autowired(required = true)
    MongoTemplate mongoTemplate;

    @Autowired(required = true)
    Location location;

    @Autowired(required = true)
    Response response;

    final static Logger log = Logger.getLogger(JsonService.class);

//    public String CREATE_HOTEL_URL = "https://jck-extranet.axisrooms.com/api/be/createBasicHotel";
//    public String ADD_ROOM_URL = "https://jck-extranet.axisrooms.com/api/be/addRoom";
//    public String HOTEL_AMENITIES_URL = "https://jck-extranet.axisrooms.com/api/be/updateHotelAmenities";
//    public String HOTEL_POLICY_URL = "https://jck-extranet.axisrooms.com/api/be/updateHotelPolicy";
//    public String MAP_LOCATION_URL = "https://jck-extranet.axisrooms.com/api/be/updateMapLocation";

    public String CREATE_HOTEL_URL = "https://jckstaging-extranet.axisrooms.com/api/be/createBasicHotel";
    public String ADD_ROOM_URL = "https://jckstaging-extranet.axisrooms.com/api/be/addRoom";
    public String HOTEL_AMENITIES_URL = "https://jckstaging-extranet.axisrooms.com/api/be/updateHotelAmenities";
    public String HOTEL_POLICY_URL = "https://jckstaging-extranet.axisrooms.com/api/be/updateHotelPolicy";
    public String MAP_LOCATION_URL = "https://jckstaging-extranet.axisrooms.com/api/be/updateMapLocation";

    ObjectMapper MAPPER = new ObjectMapper();
    Map<String,String> stateMap=new HashMap<>();
    Map<String, String> cityMap = new HashMap<>();
    Map<String, String> locationMap = new HashMap<>();
    Map<Long,String> validHotel=new HashMap<>();
    Map<Long,String> inValidHotel=new HashMap<>();
    static Long successcounter = 1l;
    static Long errorCounter = 1l;
    static Long counter=1l;
    public String pushStaticDumpDataToExtranet() {

        Query query = new Query();
        try {
            Pageable pageable = PageRequest.of(20, 2);
            query.skip(10000);
            query.addCriteria(Criteria.where("ota").is("TRAVELGURU")).with(pageable);
            List<StaticDumpHotel> staticDumpHotels = mongoTemplate.find(query, StaticDumpHotel.class);
            if (!(staticDumpHotels.isEmpty())) {
                for (StaticDumpHotel staticDumpHotel : staticDumpHotels) {
                    if(staticDumpHotel!=null) {
                        log.info("");
                        log.info("********************************No ->"+counter+". ****************************************************");
                        counter++;
                        WriteObjectToFile(staticDumpHotel);
                    }
                }
            }
            log.info(validHotel);
            log.info(inValidHotel);
            log.info("***Finish***Inserted Hotel details saved");
        } catch (Exception ex) {
            log.info(ex.toString());
        }
        return "***Finish***";
    }
    public void WriteObjectToFile(StaticDumpHotel serObj) {
        try {
            // Save Basic Hotel Details
            createBasicHotel(serObj);
            if (null != response) {
                if (response.getResult()!= null) {
                    validHotel.put(successcounter,"HotelId - "+response.getResult().getHotelId());
                    successcounter++;
                    JSONObject obj = new JSONObject(response);
                    Long NEW_HOTEL_ID = obj.getJSONObject("result").getLong("hotelId");
                    Long NEW_PRODUCT_ID = obj.getJSONObject("result").getLong("productId");

                    if (NEW_HOTEL_ID != 0l) updateMapLocation(serObj, NEW_HOTEL_ID.toString());
                    if (NEW_PRODUCT_ID != 0l) addRoom(serObj, NEW_PRODUCT_ID.toString());
                    if (NEW_HOTEL_ID != 0l) updateHotelPolicy(serObj, NEW_HOTEL_ID.toString());

                }else{
                    inValidHotel.put(errorCounter,"Hotel Id-"+serObj.get_id()+"-"+response.getError().getErrorMessage());
                    errorCounter++;
                }
            }else{
                Error error = gerError("Server Error Response From Extranet",serObj.get_id());
                log.error(error.toString());
            }
        } catch (Exception e) {
            log.info(e.toString());
        }
    }
    private String sendRestTemplate(String jsonInString, String URL) {
        String RESPONSE_DATA = new String();
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("apikey", "6ac26a4cb838a51fb5406b724ba2c43c4110f133");
            headers.add("channelId", "9");
            headers.add("access_key", "c6533c7cf985b3e4f6f84eaa5ac54a263ba9f1fdb4bd0f915d891d33e5b4b64e");
            headers.add("Content-Type", "application/json");
            HttpEntity<String> request = new HttpEntity<>(jsonInString, headers);
            RESPONSE_DATA = restTemplate.postForObject(URL, request, String.class);
            response = MAPPER.readValue(RESPONSE_DATA, Response.class);
        } catch (Exception ex) {
            Error error =gerError(ex.getMessage(),response.getError().errorCode);
            log.error("Exception : {}", ex);
        }
        return RESPONSE_DATA;
    }

    private void createBasicHotel(StaticDumpHotel staticDumpHotel) {
        try {
            StaticDumpHotelWrapper staticDumpHotelWrapper = new StaticDumpHotelWrapper();
            Information information = staticDumpHotel.getInformation();

            staticDumpHotelWrapper.hotelId = "0";
            staticDumpHotelWrapper.supplierId = "1847";
            staticDumpHotelWrapper.builtYear = "2019";
            if (!information.getName().isEmpty()) staticDumpHotelWrapper.hotelName = information.getName();else staticDumpHotelWrapper.hotelName = "***Not avalable***";
            if (information.getName() != null) staticDumpHotelWrapper.displayName = information.getName();else staticDumpHotelWrapper.displayName = "***Not avalable***";
            staticDumpHotelWrapper.hotelType = new ArrayList<>();
            if (information.getStarRating() != null) staticDumpHotelWrapper.starRating = information.getStarRating();else staticDumpHotelWrapper.starRating = "-1";
            staticDumpHotelWrapper.chainName = "118";
            staticDumpHotelWrapper.currency = "1";
            if (information.getCheckInTime() != null) staticDumpHotelWrapper.checkInTime = information.getCheckInTime();else staticDumpHotelWrapper.checkInTime = "***Not avalable***";
            if (information.getCheckOutTime() != null) staticDumpHotelWrapper.checkOutTime = information.getCheckOutTime();else staticDumpHotelWrapper.checkOutTime = "***Not avalable***";
            staticDumpHotelWrapper.country = "1";

            if(stateMap.containsValue(information.getState().toUpperCase())) {
                for (Map.Entry<String, String> entry : stateMap.entrySet()) {
                    if (entry.getValue().equals(information.getState().toUpperCase())) {
                        staticDumpHotelWrapper.state=entry.getKey();
                    }
                }
            }else{
                staticDumpHotelWrapper.state=location.getState(information.getState().toUpperCase());
                stateMap.put(staticDumpHotelWrapper.state,information.getState().toUpperCase());
            }
            if(!staticDumpHotelWrapper.state.equals("0")) {
                if(cityMap.containsValue(information.getCity().toUpperCase())) {
                    for (Map.Entry<String, String> entry : cityMap.entrySet()) {
                        if (entry.getValue().equals(information.getCity().toUpperCase())) {
                            staticDumpHotelWrapper.city=entry.getKey();
                        }
                    }
                }else{
                    staticDumpHotelWrapper.city=location.getCity(information.getCity().toUpperCase(),staticDumpHotelWrapper.state);
                    cityMap.put(staticDumpHotelWrapper.city,information.getCity());
                }
            }else{
               staticDumpHotelWrapper.state="0";
               staticDumpHotelWrapper.city="0";
            }
            if (information.getLocation()!= null && staticDumpHotelWrapper.city != "0") {
                if(locationMap.containsValue(information.getLocation().toUpperCase())) {
                    for (Map.Entry<String, String> entry : locationMap.entrySet()) {
                        if (entry.getValue().equals(information.getLocation().toUpperCase())) {
                            staticDumpHotelWrapper.locality=entry.getKey();
                        }
                    }
                }else{
                    staticDumpHotelWrapper.locality=location.getLocations(information.getLocation().toUpperCase(),staticDumpHotelWrapper.city);
                    stateMap.put(staticDumpHotelWrapper.locality,information.getLocation());
                }
            } else staticDumpHotelWrapper.locality = "0";
            staticDumpHotelWrapper.locationName = "***Not avalable***";
            staticDumpHotelWrapper.newLocation = "***Not avalable***";
            if (information.getPostalCode() != null) staticDumpHotelWrapper.zipcode = information.getPostalCode();else staticDumpHotelWrapper.zipcode = "0";
            //address line 1
            if (information.getAddress1() != null) staticDumpHotelWrapper.streetAddress = information.getAddress1();else staticDumpHotelWrapper.streetAddress = "***Not avalable***";
            if (information.getOverview() != null) staticDumpHotelWrapper.description = information.getOverview();else staticDumpHotelWrapper.description = "***Not avalable***";
            staticDumpHotelWrapper.commission = "10";
            if (information.getVendorCode() != null) staticDumpHotelWrapper.vendorCode = information.getVendorCode();else staticDumpHotelWrapper.vendorCode = "";
            if (information.getTripAdvisorRatingId() != null) staticDumpHotelWrapper.tripAdvisorRatingId = information.getTripAdvisorRatingId();else staticDumpHotelWrapper.tripAdvisorRatingId = "";
            staticDumpHotelWrapper.gstNumber = staticDumpHotel.get_id();
            staticDumpHotelWrapper.videoURL = "***Not avalable***";
            staticDumpHotelWrapper.propertyTheme = Arrays.asList("Adventure");
            if (information.getLatitude() != null) staticDumpHotelWrapper.latitude = information.getLatitude();else staticDumpHotelWrapper.latitude = "***Not avalable***";
            if (information.getLongitude() != null) staticDumpHotelWrapper.longitude = information.getLongitude();else staticDumpHotelWrapper.longitude = "***Not avalable***";
            staticDumpHotelWrapper.dmid = "1";
            staticDumpHotelWrapper.pid = "1";
            staticDumpHotelWrapper.sellerName = "TRAVELGURU";
            if (information.getNoOfRooms() != null) staticDumpHotelWrapper.noOfRooms = information.getNoOfRooms();else staticDumpHotelWrapper.noOfRooms = "-1";
            staticDumpHotelWrapper.noOfRestaurent = "0";
            staticDumpHotelWrapper.bookingWindows = Arrays.asList("Today");
            staticDumpHotelWrapper.propHighlights = Arrays.asList("TRAVELGURU", "JCK");
            if (information.getNoOfFloors() != null) staticDumpHotelWrapper.noOfFloors = information.getNoOfFloors();else staticDumpHotelWrapper.noOfFloors = "-1";
            // Check Hotel name is valid name or not.
            if (location.isAlpha(staticDumpHotelWrapper.hotelName)) {
                //Check State and city is present in db or not.
                if (!(staticDumpHotelWrapper.state.equals("0") && staticDumpHotelWrapper.city.equals("0")))
                    //Calling Rest template API and get some response.
                    log.info("Log for Hotel info added " + sendRestTemplate(MAPPER.writeValueAsString(staticDumpHotelWrapper), CREATE_HOTEL_URL));
                else {
                    Error error = gerError("State: " + information.getState() + " City : " + information.getCity() + " is not Found",staticDumpHotel.get_id());
                    log.error(error.toString());
                }
            } else {
                Error error =gerError("Hotel Name:(" + information.getName() + ") is not Valid",staticDumpHotel.get_id());
                log.error(error.toString());
            }
        } catch (Exception ex) {
            log.error("Exception : {}", ex);
        }
    }
    private void addRoom(StaticDumpHotel staticDumpHotel, String productId) {
        try {
            AddRoomWrapper addRoomWrapper = new AddRoomWrapper();
            Information information = staticDumpHotel.getInformation();
            List<RoomInformation> list = staticDumpHotel.getRoomInformations();
            if(null!=staticDumpHotel.getRoomInformations()) {
                if (!list.isEmpty()) {
                    for (RoomInformation roomInformation : list) {
                        addRoomWrapper.id = 0;
                        addRoomWrapper.productId = Long.parseLong(productId);
                        if (roomInformation.getRoomType() != null) addRoomWrapper.displayName = roomInformation.getRoomType();else addRoomWrapper.displayName = "***Not avalable***";
                        addRoomWrapper.userid = "1848";
                        if (roomInformation.getRoomType() != null) addRoomWrapper.type = roomInformation.getRoomType();else addRoomWrapper.type = "***Not avalable***";
                        addRoomWrapper.isDorm = false;
                        if (roomInformation.getDescription() != null)addRoomWrapper.description = roomInformation.getDescription();else addRoomWrapper.description = "***Not avalable***";
                        if (information.getNoOfRooms() != null)addRoomWrapper.totalRoom = Integer.parseInt(information.getNoOfRooms());else addRoomWrapper.totalRoom = -1;
                        addRoomWrapper.bedType = "double";
                        if (roomInformation.getView() != null) addRoomWrapper.roomView = roomInformation.getView();else addRoomWrapper.roomView = "***Not avalable***";
                        addRoomWrapper.roomViews = "***Not avalable***";
                        addRoomWrapper.extraBedType = "***Not avalable***";
                        if (roomInformation.getMaxAdultOccupancy() != null) addRoomWrapper.maxAdults = Integer.parseInt(roomInformation.getMaxAdultOccupancy());else addRoomWrapper.maxAdults = -1;
                        if (roomInformation.getMaxChildOccupancy() != null) addRoomWrapper.maxChild = Integer.parseInt(roomInformation.getMaxChildOccupancy());else addRoomWrapper.maxChild = -1;
                        if (roomInformation.getMaxInfantOccupancy() != null) addRoomWrapper.maxInfant = Integer.parseInt(roomInformation.getMaxInfantOccupancy());else addRoomWrapper.maxInfant = -1;
                        addRoomWrapper.isSmoking = "false";
                        if (null != roomInformation.getAmenities() && !roomInformation.getAmenities().isEmpty()) {
                            List<RoomAmenity> amenitiesList = roomInformation.getAmenities();
                            String roomAmenitiesList = amenitiesList.stream().map(RoomAmenity::getDescription).collect(Collectors.joining(","));
                            addRoomWrapper.amenities = Arrays.asList(roomAmenitiesList);
                        } else {
                            addRoomWrapper.amenities = Arrays.asList("***Not avalable***");
                        }
                        addRoomWrapper.roomSize = "-1";
                        addRoomWrapper.extraAdultAfterX = "0";
                        if (roomInformation.getMaxGuestOccupancy() != null) addRoomWrapper.baseOccupancy = Integer.parseInt(roomInformation.getMaxGuestOccupancy());else addRoomWrapper.baseOccupancy = -1;
                        log.info("Log for Room information" + sendRestTemplate(MAPPER.writeValueAsString(addRoomWrapper), ADD_ROOM_URL));
                    }
                }
                }else {
                Error error=gerError("Rooms not avalable ",staticDumpHotel.get_id());
                log.info(error.toString());
            }
        } catch (Exception ex) {
            log.error("Exception : {}", ex);
        }

    }

    public void updateHotelPolicy(StaticDumpHotel staticDumpHotel, String hotelId) {
        try {
            UpdateHotelPolicyWrapper hotelPolicyWrapper=new UpdateHotelPolicyWrapper();
            hotelPolicyWrapper.hotelId = hotelId;
            if (staticDumpHotel.getHotelPolicies().toString() != null)
                hotelPolicyWrapper.hotelPolicy = staticDumpHotel.getHotelPolicies().toString();
            else hotelPolicyWrapper.hotelPolicy = "*** Not Avalable***";
            log.info("Log for updateHotelPolicy " + sendRestTemplate(MAPPER.writeValueAsString(hotelPolicyWrapper), HOTEL_POLICY_URL));
        } catch (Exception ex) {
            log.error("Exception : {}", ex);
        }
    }

    public void updateMapLocation(StaticDumpHotel staticDumpHotel, String hotelId) {
        try {
            UpdateMapLocationWrapper mapLocationWrapper=new UpdateMapLocationWrapper();
            Information information = staticDumpHotel.getInformation();
            mapLocationWrapper.hotelId = hotelId;
            if (information.getLatitude() != null) mapLocationWrapper.latitude = information.getLatitude();
            else mapLocationWrapper.latitude = "0";
            if (information.getLongitude() != null) mapLocationWrapper.longitude = information.getLongitude();
            else mapLocationWrapper.longitude = "0";
            log.info("Log for updateMapLocation" + sendRestTemplate(MAPPER.writeValueAsString(mapLocationWrapper), MAP_LOCATION_URL));
        } catch (Exception ex) {
            log.error("Exception : {}", ex);
            ;
        }
    }
    /* public void updateHotelAmenities(StaticDumpHotel staticDumpHotel,String hotelId){
     String responseData="";
         UpdateHotelAmenitiesWrapper hotelAmenitiesWrapper=new UpdateHotelAmenitiesWrapper();

         hotelAmenitiesWrapper.hotelId=hotelId;
         List<String> amenitiesIDslist=new ArrayList<>();
         List<HotelAmenity>hotelAmenities=staticDumpHotel.getHotelAmenities();
         for(HotelAmenity hotelAmenity:hotelAmenities){
             hotelAmenity.getName();
             hotelAmenity.getDescription();
             hotelAmenity.getCategory();
             hotelAmenity.getAmenityType();
            // responseData=sendRestTemplate(MAPPER.writeValueAsString(hotelAmenity),HOTEL_AMENITIES_URL);
         }
         hotelAmenitiesWrapper.aminetiesList=amenitiesIDslist;
         try {
             log.info("Log for HotelAmenities "+sendRestTemplate(MAPPER.writeValueAsString(hotelAmenitiesWrapper),HOTEL_AMENITIES_URL));
         }catch (Exception e){

         }
     }*/
   /* public void createHotelImages(StaticDumpHotel staticDumpHotel, String hotelId){
        HotelImagesWrapper hotelImagesWrapper=new HotelImagesWrapper();
        RoomDetail roomDetail=new RoomDetail();
        Information information = staticDumpHotel.getInformation();
        Map<String,String> hotelImageMap=new HashMap<>();
        Map<String,String> roomImageMap=new HashMap<>();
        hotelImagesWrapper.hotelId=hotelId;
        List<Image> list=staticDumpHotel.getImages();
         try {
            log.info("Log for Hotel Images "+sendRestTemplate(MAPPER.writeValueAsString(hotelImagesWrapper),MAP_LOCATION_URL));
        }catch (Exception e){e.printStackTrace(); }
    }
    public void createRoomImages(StaticDumpHotel staticDumpHotel, String hotelId){
        HotelImagesWrapper hotelImagesWrapper=new HotelImagesWrapper();
        RoomDetail roomDetail=new RoomDetail();
        Information information = staticDumpHotel.getInformation();
        Map<String,String> hotelImageMap=new HashMap<>();
        Map<String,String> roomImageMap=new HashMap<>();
        hotelImagesWrapper.hotelId=hotelId;
        List<Image> list=staticDumpHotel.getImages();
        try {
            log.info("Log for Hotel Images "+sendRestTemplate(MAPPER.writeValueAsString(hotelImagesWrapper),MAP_LOCATION_URL));
        }catch (Exception e){e.printStackTrace(); }
    }*/

   public Error gerError(String message,String Id){
       Error error = new Error();
       error.setErrorCode(Id);
       error.setErrorMessage(message);
       response.setError(error);
       return error;
   }
}
