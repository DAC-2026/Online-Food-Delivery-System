package com.backend.service;

import java.util.List;

import com.backend.Entity.Restaurant;
import com.backend.dto.RestaurantDto;

public interface RestaurantService {
	List<RestaurantDto> getAllRestaurants();

	RestaurantDto getRestaurantById(Long id);
	
	RestaurantDto createRestaurant(com.backend.dto.CreateRestaurantRequest request);
	
	RestaurantDto updateRestaurant(Long id, com.backend.dto.CreateRestaurantRequest request);
	
	void deleteRestaurant(Long id);
}
