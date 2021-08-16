package com.effective.couchdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Event {
    @JsonProperty("_id")
    private String id;

    @JsonProperty("_rev")
    private String revision;

    private Long createdAt;
    private String type;
    private Object data;
}
