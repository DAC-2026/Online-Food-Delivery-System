package com.backend.service.impl;
import com.backend.dto.CreateRestaurantRequest;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.Entity.Restaurant;
import com.backend.Repository.RestaurantRepository;
import com.backend.dto.RestaurantDto;
import com.backend.exception.ResourceNotFoundException;
import com.backend.service.RestaurantService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
	private final RestaurantRepository restaurantRepository ;
	private final ModelMapper modelMapper;
	@Override
	public List<RestaurantDto> getAllRestaurants() {
		
		return restaurantRepository.findAll().stream().map(restaurant -> modelMapper.map(restaurant, RestaurantDto.class)).toList();
	}
	@Override
	public RestaurantDto getRestaurantById(Long id) {
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Restaurant not found with id: " + id));
		return modelMapper.map(restaurant, RestaurantDto.class );
	}

	@Override
	public RestaurantDto createRestaurant(CreateRestaurantRequest request) {
		Restaurant restaurant = modelMapper.map(request, Restaurant.class);
		Restaurant savedRestaurant = restaurantRepository.save(restaurant);
		return modelMapper.map(savedRestaurant, RestaurantDto.class);
	}

	@Override
	public RestaurantDto updateRestaurant(Long id, CreateRestaurantRequest request) {
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));

		modelMapper.map(request, restaurant);
		Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
		return modelMapper.map(updatedRestaurant, RestaurantDto.class);
	}


}
