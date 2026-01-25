package com.backend.service;

import java.util.List;
import com.backend.dto.RestaurantDto;
import com.backend.dto.CreateRestaurantRequest;
public interface RestaurantService {
	List<RestaurantDto> getAllRestaurants();

	RestaurantDto getRestaurantById(Long id);
	
	RestaurantDto createRestaurant(CreateRestaurantRequest request);
	
	RestaurantDto updateRestaurant(Long id, CreateRestaurantRequest request);
}
