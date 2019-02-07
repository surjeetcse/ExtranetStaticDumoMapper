package com.example.demo.generatedclasses.common.bean.scrunch;

import com.example.demo.generatedclasses.common.enums.staticdump.AmenityType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelAmenity {
    //Name of the amenity
    private String name;
    //More information about the amenity
    private String description;
    //What is the type of the amenity
    private String category;
    //At what level does the amenity apply - at a property level/room level. Though this is a hotelAmenity, there is a mix at times.
    private AmenityType amenityType;

    @Tolerate
    public HotelAmenity() {

    }
}
