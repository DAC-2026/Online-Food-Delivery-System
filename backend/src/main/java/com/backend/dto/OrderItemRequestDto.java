package com.backend.dto;

import lombok.Data;

@Data
public class OrderItemRequestDto {
    private Long menuItemId;
    private Integer quantity;
}
