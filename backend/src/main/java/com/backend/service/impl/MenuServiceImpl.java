package com.backend.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.Entity.MenuCategory;
import com.backend.Entity.MenuItem;
import com.backend.Entity.Restaurant;
import com.backend.Repository.MenuCategoryRepository;
import com.backend.Repository.MenuItemRepository;
import com.backend.Repository.RestaurantRepository;
import com.backend.dto.MenuCategoryRequest;
import com.backend.dto.MenuCategoryResponse;
import com.backend.dto.MenuItemRequest;
import com.backend.dto.MenuItemResponse;
import com.backend.dto.RestaurantDto;
import com.backend.exception.ResourceNotFoundException;
import com.backend.service.MenuService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
	private final MenuCategoryRepository menuCategoryRepository;
	private final RestaurantRepository restaurantRepository;
	private final MenuItemRepository menuItemRepository;
	private final ModelMapper modelMapper;
	@Override
	public List<MenuCategoryResponse> getAllCategories(Long id) {
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Restaurant Not found"));
		return menuCategoryRepository.findCategoriesByRestaurant(restaurant)
				.stream()
				.map(menuCategory ->modelMapper.map(menuCategory, MenuCategoryResponse.class))
				.toList();
	}
	@Override
	public List<MenuItemResponse> getMenuItemsByRestaurantId(Long id) {
		return menuItemRepository.findMenuItemsByRestaurantId(id)
				.stream()
				.map(menuItem -> modelMapper.map(menuItem, MenuItemResponse.class))
				.toList();
	}
	@Override
	public List<MenuItemResponse> getAllMenuItems() {
		return menuItemRepository.findAll()
				.stream()
				.map(menuItem -> modelMapper.map(menuItem, MenuItemResponse.class))
				.toList();
	}
	@Override
	public List<MenuItemResponse> getMenuItemsByCategory(Long id) {
		MenuCategory category = menuCategoryRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Category Not found"));
		return menuItemRepository.findByCategory(category)
				.stream()
				.map(menuItem ->modelMapper.map(menuItem, MenuItemResponse.class))
				.toList();
	}

	@Override
	public MenuCategoryResponse createCategory(Long restaurantId, MenuCategoryRequest categoryDto) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));
		
		MenuCategory category = modelMapper.map(categoryDto, MenuCategory.class);
		category.setRestaurant(restaurant);
		
		MenuCategory savedCategory = menuCategoryRepository.save(category);
		return modelMapper.map(savedCategory, MenuCategoryResponse.class);
	}

	@Override
	public MenuCategoryResponse updateCategory(Long categoryId, MenuCategoryRequest categoryDto) {
		MenuCategory category = menuCategoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
//		modelMapper.map(categoryDto, MenuCategory.class);
		category.setName(categoryDto.getName()!=null?categoryDto.getName():category.getName());
		category.setDescription(categoryDto.getDescription()!=null?categoryDto.getDescription():category.getDescription());
		category.setImageUrl(categoryDto.getImageUrl()!=null ? categoryDto.getImageUrl():category.getImageUrl());
		MenuCategory updatedCategory = menuCategoryRepository.save(category);
		return modelMapper.map(updatedCategory, MenuCategoryResponse.class);
	}

	@Override
	public MenuItemResponse createMenuItem(Long categoryId, MenuItemRequest menuItemDto) {
		MenuCategory category = menuCategoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
		
		com.backend.Entity.MenuItem menuItem = modelMapper.map(menuItemDto, com.backend.Entity.MenuItem.class);
		menuItem.setCategory(category);
		
		com.backend.Entity.MenuItem savedMenuItem = menuItemRepository.save(menuItem);
		return modelMapper.map(savedMenuItem, MenuItemResponse.class);
	}

	@Override
	public MenuItemResponse updateMenuItem(Long itemId, MenuItemRequest menuItemDto) {
		com.backend.Entity.MenuItem menuItem = menuItemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu Item not found with id: " + itemId));
		
		modelMapper.map(menuItemDto, menuItem);
		com.backend.Entity.MenuItem updatedMenuItem = menuItemRepository.save(menuItem);
		return modelMapper.map(updatedMenuItem, MenuItemResponse.class);
	}
	


}
