package com.backend.service;

import com.backend.dto.PlaceOrderRequestDto;
import com.backend.dto.OrderDetailsResponse;
import com.backend.dto.OrderResponse;

import java.util.List;

public interface OrderService {
     OrderResponse placeOrder(PlaceOrderRequestDto request);
     List<OrderResponse> getUserOrders(Long userId);
     OrderDetailsResponse getOrderDetails(Long orderId);
}
