package com.example.demo.ServerResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Service
public class Result {
    private String productstatus;
    private Long productId;
    private Long hotelId;
    private String hotelName;
    private String status;
}
