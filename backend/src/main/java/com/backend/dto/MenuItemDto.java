package com.backend.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemDto {
	private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal rating;
    private String imageUrl;
    private Boolean isVeg;
    private Boolean isAvailable;
    private Integer preparationTime;
}
