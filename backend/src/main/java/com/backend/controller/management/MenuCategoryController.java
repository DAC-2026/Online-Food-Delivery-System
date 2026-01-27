package com.backend.controller.management;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.MenuCategoryDto;
import com.backend.service.MenuService;
import com.backend.utils.ApiPath;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.V1 + "/management")
@RequiredArgsConstructor
@Validated
@CrossOrigin("http://localhost:5173/") 
public class MenuCategoryController {

	private final MenuService menuService;

	@PostMapping("/{restaurantId}/categories")
	@Operation(description = "Create a new Menu Category")
	public ResponseEntity<MenuCategoryDto> createCategory(@PathVariable Long restaurantId,
			@RequestBody @Valid MenuCategoryDto categoryDto) {
		return new ResponseEntity<>(menuService.createCategory(restaurantId, categoryDto), HttpStatus.CREATED);
	}

	@PutMapping("/categories/{categoryId}")
	@Operation(description = "Update an existing Menu Category")
	public ResponseEntity<MenuCategoryDto> updateCategory(@PathVariable Long categoryId,
			@RequestBody MenuCategoryDto categoryDto) {
		return ResponseEntity.ok(menuService.updateCategory(categoryId, categoryDto));
	}
}
