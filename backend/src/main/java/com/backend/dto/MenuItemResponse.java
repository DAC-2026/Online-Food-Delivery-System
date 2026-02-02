package com.backend.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemResponse {
    private Long itemId;
    private Long id; // for Mapping with itemId used in frontEnd
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal rating;
    private String imageUrl;
    private Boolean isVeg;
    private Boolean isAvailable;
    private Integer preparationTime;
    public void setId(Long id) {
        this.id = id;
        this.itemId = id;   // sync both
    }
}
