package com.backend.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.Entity.MenuCategory;
import com.backend.Entity.Restaurant;
import com.backend.Repository.MenuCategoryRepository;
import com.backend.Repository.RestaurantRepository;
import com.backend.dto.MenuCategoryDto;
import com.backend.dto.MenuItemDto;
import com.backend.dto.RestaurantDto;
import com.backend.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
	private final MenuCategoryRepository menuCategoryRepository;
	private final RestaurantRepository restaurantRepository;
	private final ModelMapper modelMapper;
	@Override
	public List<MenuCategoryDto> getAllCategories(Long id) {
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Restaurant Not found"));
		return menuCategoryRepository.findCategoriesByRestaurant(restaurant)
				.stream()
				.map(menuCategory ->modelMapper.map(menuCategory, MenuCategoryDto.class))
				.toList();
	}
	@Override
	public List<MenuItemDto> getMenuItems(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
