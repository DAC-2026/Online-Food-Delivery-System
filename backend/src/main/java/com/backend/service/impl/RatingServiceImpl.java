package com.backend.service.impl;

import org.springframework.stereotype.Service;
import com.backend.dto.RatingResponse;

import com.backend.Entity.MenuItem;
import com.backend.Entity.MenuItemRating;
import com.backend.Entity.Restaurant;
import com.backend.Entity.User;
import com.backend.Repository.MenuItemRatingRepository;
import com.backend.Repository.MenuItemRepository;
import com.backend.Repository.RestaurantRepository;
import com.backend.Repository.UserRepository;
import com.backend.dto.RatingRequest;
import com.backend.exception.ResourceNotFoundException;
import com.backend.service.RatingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

	private final MenuItemRatingRepository menuItemRatingRepository;
	private final MenuItemRepository menuItemRepository;
	private final RestaurantRepository restaurantRepository;
	private final UserRepository userRepository;

	@Override
	public RatingResponse addRating(RatingRequest request) {
		// Validating User
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

		// Validating Restaurant
		Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Restaurant not found with id: " + request.getRestaurantId()));

		// Validating MenuItem
		MenuItem menuItem = menuItemRepository.findById(request.getMenuItemId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Menu Item not found with id: " + request.getMenuItemId()));

		// Check if Rating already exists
		boolean ratingExists = menuItemRatingRepository.findByMenuItemIdAndUserId(request.getMenuItemId(),
				request.getUserId()).isPresent();

		if (ratingExists) {
			throw new RuntimeException("Rating already exists for this menu item by user");
		}

		MenuItemRating menuItemRating = new MenuItemRating();
		menuItemRating.setMenuItem(menuItem);
		menuItemRating.setRestaurant(restaurant);
		menuItemRating.setUser(user);
		menuItemRating.setRating(request.getRating());

		menuItemRatingRepository.save(menuItemRating);
		return mapToResponse(menuItemRating);
		
	}

	@Override
	public RatingResponse updateRating(RatingRequest request) {
		MenuItemRating menuItemRating = menuItemRatingRepository
				.findByMenuItemIdAndUserId(request.getMenuItemId(), request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("Rating not found for this menu item and user"));

		menuItemRating.setRating(request.getRating());

		menuItemRatingRepository.save(menuItemRating);
		return mapToResponse(menuItemRating);
	}
	
	private RatingResponse mapToResponse(MenuItemRating rating) {
		RatingResponse response = new RatingResponse();
		response.setId(rating.getId());
		response.setMenuItemId(rating.getMenuItem().getId());
		response.setRestaurantId(rating.getRestaurant().getId());
		response.setUserId(rating.getUser().getId());
		response.setRating(rating.getRating());
		return response;
	}
}
