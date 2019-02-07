package com.example.demo.generatedclasses.common.bean.scrunch;

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
public class PointOfInterest {

    private String nameOfAttraction;
    private String distanceInKm;
    private String description;
    @Tolerate
    public PointOfInterest() {

    }
}
