package com.example.demo.generatedclasses.common.bean.scrunch;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "x",
        "y"
})
public class Point implements Serializable {

    @JsonProperty("x")
    public double x;
    @JsonProperty("y")
    public double y;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 9114259791528791186L;

    /**
     * No args constructor for use in serialization
     */
    public Point() {
    }

    /**
     * @param y
     * @param x
     */
    public Point(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    public Point withX(double x) {
        this.x = x;
        return this;
    }

    public Point withY(double y) {
        this.y = y;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Point withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}