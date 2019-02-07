package com.example.demo.generatedclasses.common.bean.scrunch;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomInformation {

    private String roomTypeId;
    private String roomType;
    private String description;
    private String maxAdultOccupancy;
    private String maxChildOccupancy;
    private String maxInfantOccupancy;
    private String maxGuestOccupancy;
    private String roomTypeImagePath;
    private List<RoomAmenity> amenities;
    private String view;
    @Tolerate
    public RoomInformation() {

    }
}
