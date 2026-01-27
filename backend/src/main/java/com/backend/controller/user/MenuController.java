package com.backend.controller.user;

import static com.backend.utils.ApiPath.V1;

import com.backend.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.exception.ResourceNotFoundException;
import com.backend.service.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(V1)
@RequiredArgsConstructor
@Validated
@CrossOrigin("http://localhost:5173/")
public class MenuController {
	
	private final MenuService menuService;
	@GetMapping("/{restaurantId}/categories")
	@Operation(description = "Get menu Categories By Restaurant Id")
	public ResponseEntity<?> getAllCategories(@PathVariable("restaurantId") @Min(1) Long id){
			return ResponseEntity.ok(menuService.getAllCategories(id));
	}
	
	@GetMapping("/categories/{categoryId}/items")
	@Operation(description = "Get menu items By Category Id")
	public ResponseEntity<?>  getMenuItemsByCategoryId(@PathVariable("categoryId") @Min(1) Long id){
			return ResponseEntity.ok(menuService.getMenuItemsByCategory(id));
	}
	@GetMapping("/restaurant/{restaurantId}/items")
	@Operation(description = "Get menu items By Restaurant Id")
	public ResponseEntity<?>  getMenuItemsByRestaurantId(@PathVariable("restaurantId") @Min(1) Long id){
			return ResponseEntity.ok(menuService.getMenuItemsByRestaurantId(id));
	}
	
	
	
}
