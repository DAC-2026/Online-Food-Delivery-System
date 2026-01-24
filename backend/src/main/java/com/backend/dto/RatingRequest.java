package com.backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingRequest {
	private Long menuItemId;
	private Long restaurantId;
	private Long userId;
	@NotNull(message = "Rating must not be null")
    @DecimalMin(value = "1.0", message = "Rating must be at least 1" )
    @DecimalMax(value = "5.0", message = "Rating must be at most 5")
	private BigDecimal rating;
}
