
package com.example.demo.generatedclasses.common.bean.scrunch;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "$date"
})
public class UpdateDate implements Serializable
{

    @JsonProperty("$date")
    public String $date;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateDate() {
    }

    /**
     * 
     * @param $date
     */
    public UpdateDate(String $date) {
        super();
        this.$date = $date;
    }

    public UpdateDate with$date(String $date) {
        this.$date = $date;
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

    public UpdateDate withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
