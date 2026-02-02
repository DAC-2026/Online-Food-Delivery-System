package com.backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequest {
	
	@NotBlank(message = "Label is required")
    private String label;

	@NotBlank(message = "Address Line is required")
    private String addressLine;

	@NotBlank(message = "City is required")
    private String city;

	@NotBlank(message = "Pincode is required")
    private String pincode;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
