package com.backend.service;

import java.util.List;

import com.backend.Repository.RestaurantDto;

public interface RestaurantService {
	List<RestaurantDto> getAllRestaurants();

	RestaurantDto getRestaurantById(Long id);
}
