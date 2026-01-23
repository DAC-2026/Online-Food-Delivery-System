package com.backend.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.Entity.Restaurant;
import com.backend.Repository.RestaurantRepository;
import com.backend.dto.RestaurantDto;
import com.backend.exception.ResourceNotFoundException;

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
	public Restaurant getRestaurantById(Long id) {
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Restaurant Not found"));
		return modelMapper.map(restaurant, Restaurant.class );
	}

}
