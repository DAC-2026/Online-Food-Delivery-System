package com.backend.Repository;

import java.math.BigDecimal;
import com.backend.constants.AvailabilityStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDto {
	
	private Long id;
    private String name;
    private BigDecimal rating;
    private String description;
    private String imageUrl;
    private AvailabilityStatus availabilityStatus;
}
