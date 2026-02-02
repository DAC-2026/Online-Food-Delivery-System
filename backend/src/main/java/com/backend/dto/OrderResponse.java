package com.backend.dto;

import java.math.BigDecimal;
import java.util.List;

import com.backend.constants.OrderStatus;
import com.backend.constants.PaymentMode;
import com.backend.constants.PaymentStatus;

import lombok.Data;

@Data
public class OrderResponse {
    private Long orderId;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private BigDecimal totalAmount;
    private PaymentMode paymentMode;
    private String message;
    private List<OrderItemResponse> items;
}
