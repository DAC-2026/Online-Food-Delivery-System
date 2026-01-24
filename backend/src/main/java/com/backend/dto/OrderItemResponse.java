package com.backend.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemResponse {
    private String menuItemName;
    private Integer quantity;
    private BigDecimal price;
}
