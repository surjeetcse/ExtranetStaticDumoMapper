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
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomAmenity  {

    private String _id;
    private String name;
    private String description;
    private String category;
    private String roomId;
    private AmenityType type;
    @Tolerate
    public RoomAmenity() {

    }
}

