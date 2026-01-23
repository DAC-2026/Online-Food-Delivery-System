package com.backend.service;

import java.util.List;

import com.backend.Entity.Restaurant;
import com.backend.dto.RestaurantDto;

public interface RestaurantService {
	List<RestaurantDto> getAllRestaurants();

	Restaurant getRestaurantById(Long id);
}
