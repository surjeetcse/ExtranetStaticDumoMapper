package com.example.demo.generatedclasses.common.bean.scrunch;

import com.example.demo.generatedclasses.common.enums.staticdump.CardsAccepted;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
//import org.springframework.data.geo.Point;
//import org.springframework.data.mongodb.core.index.CompoundIndex;
//import org.springframework.data.mongodb.core.index.CompoundIndexes;
//import org.springframework.data.mongodb.core.index.Indexed;

import java.awt.Point;
import java.util.List;

@Builder
@Getter
@Setter
//There is a setter and a builder because there are instances when we have to build the object in stages and a strict builder prevents us from doing it.
@ToString
@EqualsAndHashCode(exclude = {"whatToExpect", "overview", "roomDescription"})
//@CompoundIndexes({@CompoundIndex(name = "name_city", def = "{'name' : 1, 'city': 1}")})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Information {

    private String name;
    private String chainName;
    private String chainId;
    private String hotelClass;
    @JsonIgnore
    private String location;
    private String city;

    // @Indexed
    private String country;

    //  @Indexed
    private String countryCode;
    private String address1;
    private String address2;
    private String area;
    private String usp;
    private String overview;
    private String whatToExpect;
    private String reviewRating;
    private String reviewCount;
    private String latitude;
    private String longitude;
    private Point point;

    private String checkInTime;
    private String checkOutTime;
    private String starRating;

    private String imagePath;
    private String hotelSearchKey;
    private String areaSeoId;
    private String secondaryAreaIds;
    private String secondaryAreaName;
    private String noOfFloors;
    private String noOfRooms;
    private List<RoomType> roomTypes;
    private String state;
    private String stateCode;
    private String postalCode;
    private String themeList;
    private String categoryList;
    private String cityZone;
    private String weekDayRank;
    private String weekEndRank;
    private Fees fees;
    private CardsAccepted cardsAccepted;
    private List<CommunicationInfo> communicationInfo;
    //Information about what kind of rooms are available at the property.
    private String roomDescription;
    private String vendorCode;
    private String commission;
    private String tripAdvisorRatingId;

    @Tolerate
    public Information() {

    }
}
