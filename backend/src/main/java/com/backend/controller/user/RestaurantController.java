package com.backend.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Response.ApiResponse;
import com.backend.dto.RestaurantDto;
import com.backend.exception.ResourceNotFoundException;
import com.backend.service.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import static com.backend.utils.ApiPath.V1;

import java.util.List;
@RestController
@RequestMapping(V1+"/restaurants")
@RequiredArgsConstructor
@Validated
public class RestaurantController {
	
	private final RestaurantService restaurantService;
	
	@GetMapping
	@Operation(description =  "Get all Restaurant")
	public ResponseEntity<?> getAllRestaurants(){
		List<RestaurantDto> restaurants = restaurantService.getAllRestaurants() ;
		
		if(restaurants.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(restaurants);
	}
	@GetMapping("/{restaurantId}")
	@Operation(description =  "Get Restaurant By Id")
	public ResponseEntity<?> getRestaurantDetails(@PathVariable("restaurantId") @Min(1) Long id){
		return ResponseEntity.ok(restaurantService.getRestaurantById(id));
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
