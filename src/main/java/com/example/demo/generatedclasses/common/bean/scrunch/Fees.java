package com.example.demo.generatedclasses.common.bean.scrunch;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
//There is a setter and a builder because there are instances when we have to build the object in stages and a strict builder prevents us from doing it.
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fees {

    // Descriptions for resort fees, city taxes, specific VAT and other mandatory taxes or charges.
    // May describe which services are covered by any fees, such as fitness centers or internet access.
    private String mandatory;

    //This file contains descriptions for incidental per-room and service fees, e.g. pet fees, breakfast, room deposits, parking and shuttle fees.
    private String incidental;
}