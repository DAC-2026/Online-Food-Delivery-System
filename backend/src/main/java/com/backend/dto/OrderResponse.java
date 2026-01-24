package com.backend.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponse {
    private Long orderId;
    private String orderStatus;
    private String paymentStatus;
    private BigDecimal totalAmount;
    private String paymentMode;
    private String message;
    private List<OrderItemResponse> items;
}
