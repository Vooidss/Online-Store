package ru.org.backend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdressDetails {
    private String city;
    private String street;
    private Integer numberHouse;
    private Integer numberApartment;
    private Integer numberIntercom;
}
