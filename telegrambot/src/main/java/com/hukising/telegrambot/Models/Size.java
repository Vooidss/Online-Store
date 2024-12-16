package com.hukising.telegrambot.Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Size {

    private Long id;
    private String size_value;

    @JsonCreator
    public Size(@JsonProperty("size_value") String size_value){
        this.size_value = size_value;
    }
}
