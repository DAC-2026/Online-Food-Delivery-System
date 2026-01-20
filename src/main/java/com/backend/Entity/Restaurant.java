package com.backend.Entity;

import java.math.BigDecimal;

import com.backend.constants.AvailabilityStatus;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restaurant_id")
	private Long retaurantId;
	private String name;
	private String description;
	private Double rating;
	private AvailabilityStatus isOPen;

}
