package com.backend.dto;

import java.math.BigDecimal;

import com.backend.constants.AvailabilityStatus;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRestaurantRequest {

	@NotBlank(message = "Restaurant name is required")
	private String name;

	@NotBlank(message = "Description is required")
	private String description;

	@NotNull(message = "Rating is required")
	@DecimalMin(value = "0.0", message = "Rating must be at least 0.0")
	@DecimalMax(value = "5.0", message = "Rating must be at most 5.0")
	private BigDecimal rating;

	private String imageUrl;

	@NotNull(message = "Availability status is required")
	private AvailabilityStatus availabilityStatus;
}
