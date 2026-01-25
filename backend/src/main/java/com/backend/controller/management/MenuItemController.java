package com.backend.controller.management;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.MenuItemDto;
import com.backend.service.MenuService;
import com.backend.utils.ApiPath;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.V1 + "/management")
@RequiredArgsConstructor
public class MenuItemController {

	private final MenuService menuService;

	@PostMapping("/categories/{categoryId}/items")
	@Operation(description = "Create a new Menu Item")
	public ResponseEntity<MenuItemDto> createMenuItem(@PathVariable Long categoryId,
			@RequestBody MenuItemDto menuItemDto) {
		return new ResponseEntity<>(menuService.createMenuItem(categoryId, menuItemDto), HttpStatus.CREATED);
	}

	@PutMapping("/items/{itemId}")
	@Operation(description = "Update an existing Menu Item")
	public ResponseEntity<MenuItemDto> updateMenuItem(@PathVariable Long itemId,
			@RequestBody MenuItemDto menuItemDto) {
		return ResponseEntity.ok(menuService.updateMenuItem(itemId, menuItemDto));
	}

}
