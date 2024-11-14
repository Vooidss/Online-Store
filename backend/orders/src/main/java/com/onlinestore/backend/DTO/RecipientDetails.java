package com.onlinestore.backend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipientDetails {

    private String name;
    private String secondName;
    private String phone;
}
