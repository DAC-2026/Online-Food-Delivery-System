package com.backend.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddressResponse {
    private Long addressId;
    private String label;
    private String addressLine;
    private String city;
    private String pincode;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
