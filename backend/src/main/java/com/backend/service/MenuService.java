package com.backend.service;

import java.util.List;

import com.backend.dto.MenuCategoryRequest;
import com.backend.dto.MenuCategoryResponse;
import com.backend.dto.MenuItemRequest;
import com.backend.dto.MenuItemResponse;

public interface MenuService {

	List<MenuCategoryResponse> getAllCategories(Long id);

	List<MenuItemResponse> getMenuItemsByCategory(Long id);
	List<MenuItemResponse> getMenuItemsByRestaurantId(Long id);
	List<MenuItemResponse> getAllMenuItems();
	
	MenuCategoryResponse createCategory(Long restaurantId, MenuCategoryRequest categoryDto);
	
	MenuCategoryResponse updateCategory(Long categoryId, MenuCategoryRequest categoryDto);
	
	
	MenuItemResponse createMenuItem(Long categoryId, MenuItemRequest menuItemDto);
	
	MenuItemResponse updateMenuItem(Long itemId, MenuItemRequest menuItemDto);
}
