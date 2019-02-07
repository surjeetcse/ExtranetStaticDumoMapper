package com.example.demo.staticdump.model.document;

import com.example.demo.generatedclasses.common.bean.scrunch.*;
import com.example.demo.generatedclasses.common.enums.staticdump.OTA;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
//@EqualsAndHashCode(exclude = "updateDate")
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "staticDumpHotel")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StaticDumpHotel implements Serializable {
    private String _id;
    private OTA ota;
    private Information information;
    private List<HotelAmenity> hotelAmenities;
    private List<RoomInformation> roomInformations;
    private List<PointOfInterest> pointsOfInterest;
    private List<String> hotelPolicies;
    private List<Image> images;
    @JsonProperty("updateDate")
    public String updateDate;
    private String renovationDetails;

    @Tolerate
    public StaticDumpHotel() {

    }
}
