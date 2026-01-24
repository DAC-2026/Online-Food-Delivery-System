package com.backend.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RatingResponse {
    private Long id;
    private Long menuItemId;
    private Long restaurantId;
    private Long userId;
    private BigDecimal rating;
}
