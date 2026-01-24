package com.backend.controller.management;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.CreateRestaurantRequest;
import com.backend.dto.RestaurantDto;
import com.backend.service.RestaurantService;
import com.backend.utils.ApiPath;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.V1 + "/management/restaurants")
@RequiredArgsConstructor
@Validated
public class RestaurantManagementController {

	private final RestaurantService restaurantService;

	@PostMapping
	@Operation(description = "Create a new Restaurant")
	public ResponseEntity<RestaurantDto> createRestaurant(@Valid @RequestBody CreateRestaurantRequest request) {
		return new ResponseEntity<>(restaurantService.createRestaurant(request), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@Operation(description = "Update an existing Restaurant")
	public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable Long id,
			@Valid @RequestBody CreateRestaurantRequest request) {
		return ResponseEntity.ok(restaurantService.updateRestaurant(id, request));
	}

	@DeleteMapping("/{id}")
	@Operation(description = "Delete a Restaurant")
	public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
		restaurantService.deleteRestaurant(id);
		return ResponseEntity.noContent().build();
	}
}
