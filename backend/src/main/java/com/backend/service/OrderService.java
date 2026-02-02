package com.backend.service;

import com.backend.dto.PlaceOrderRequestDto;
import com.backend.dto.OrderDetailsResponse;
import com.backend.dto.OrderResponse;

import java.util.List;

import java.math.BigDecimal;

public interface OrderService {
     BigDecimal calculateTotalOrderAmount(PlaceOrderRequestDto request);
     OrderResponse placeOrder(PlaceOrderRequestDto request);
     List<OrderResponse> getUserOrders(Long userId);
     OrderDetailsResponse getOrderDetails(Long orderId);
}
