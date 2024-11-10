package com.hukising.telegrambot.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Size {
    private Long id;
    private String size_value;
}
