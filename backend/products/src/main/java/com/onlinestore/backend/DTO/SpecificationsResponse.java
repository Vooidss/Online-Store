package com.onlinestore.backend.DTO;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class SpecificationsResponse extends Response{
    private Map<String,Long> sizes;
    private Map<String,Long> materials;
    private Map<String,Long> colors;
    private Map<String,Long> brands;
}
