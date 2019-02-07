package com.example.demo.generatedclasses.common.enums.staticdump;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public enum AmenityType {
    PROPERTY,
    ROOM,
    UNKNOWN,

    //Expedia specific
    PETS,
    CHECKINOUT,
    OTHER
}