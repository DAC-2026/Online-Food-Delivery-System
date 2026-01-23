package com.backend.service;

import java.util.List;

import com.backend.dto.MenuCategoryDto;
import com.backend.dto.MenuItemDto;

public interface MenuService {

	List<MenuCategoryDto> getAllCategories(Long id);

	List<MenuItemDto> getMenuItems(Long id);
}
