package com.backend.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Entity.MenuItemRating;
import com.backend.dto.RatingRequest;
import com.backend.dto.RatingResponse;
import com.backend.service.RatingService;
import com.backend.utils.ApiPath;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.V1 + "/ratings")
@RequiredArgsConstructor
@Validated
public class RatingController {

	private final RatingService ratingService;

	@PostMapping
	@Operation(description = "Add Rating to Menu Item")
	public ResponseEntity<?> addRating(@Valid @RequestBody RatingRequest request) {
		RatingResponse rating = ratingService.addRating(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(rating);
	}

	@PutMapping
	@Operation(description = "Update Rating of Menu Item")
	public ResponseEntity<?> updateRating(@Valid @RequestBody RatingRequest request) {
		RatingResponse rating = ratingService.updateRating(request);
		return ResponseEntity.ok(rating);
	}

	
}
