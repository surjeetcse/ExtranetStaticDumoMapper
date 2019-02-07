package com.example.demo.ServerResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Service
public class Error {
   @JsonProperty(value = "ErrorCode")
   public String errorCode;
   @JsonProperty(value = "ErrorMessage")
   public String errorMessage;

   @Override
   public String toString() {
      return "Error{" +
              "errorCode='" + errorCode + '\'' +
              ", errorMessage='" + errorMessage + '\'' +
              '}';
   }
}
