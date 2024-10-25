package com.onlinestore.backend.DTO;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class SpecificationsResponse extends Response{
    private List<Map<String,Object>> sizes;
    private List<Map<String,Object>> materials;
    private List<Map<String,Object>> colors;
    private List<Map<String,Object>> brands;
}
