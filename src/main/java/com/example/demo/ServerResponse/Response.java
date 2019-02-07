package com.example.demo.ServerResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Service
public class Response {
    private Result result;
    @JsonProperty(value = "Error")
    private Error error;
}
