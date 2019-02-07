package com.example.demo.generatedclasses.common.WrapperClasses;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.List;

public class HotelImagesWrapper implements Serializable {

    public String hotelId;
    public List<Pair<String, String>> hotelImages;
    public RoomDetail roomDetails;

    @Override
    public String toString() {
        return "HotelImagesWrapper{" +
                "hotelId='" + hotelId + '\'' +
                ", hotelImages=" + hotelImages +
                ", roomDetails=" + roomDetails +
                '}';
    }
}