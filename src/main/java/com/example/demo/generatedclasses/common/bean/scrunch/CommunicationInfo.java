package com.example.demo.generatedclasses.common.bean.scrunch;

import com.example.demo.generatedclasses.common.enums.staticdump.CommunicationCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunicationInfo {

    private String phone;
    private String email;
    private String fax;
    private String website;
    private String mobile;
    private CommunicationCategory communicationCategory;

}
