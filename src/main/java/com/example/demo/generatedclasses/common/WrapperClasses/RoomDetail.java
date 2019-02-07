package com.example.demo.generatedclasses.common.WrapperClasses;


import javafx.util.Pair;

import java.util.List;

public class RoomDetail {

    public String roomId;
    public String roomName;
    public List<Pair<String,String>> roomImages;

    @Override
    public String toString() {
        return "RoomDetail{" +
                "roomId='" + roomId + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomImages=" + roomImages +
                '}';
    }
}