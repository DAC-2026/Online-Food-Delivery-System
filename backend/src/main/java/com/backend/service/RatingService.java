package com.backend.service;


import com.backend.dto.RatingRequest;
import com.backend.dto.RatingResponse;

public interface RatingService {

	RatingResponse addRating(RatingRequest request);

	RatingResponse updateRating(RatingRequest request);
}
