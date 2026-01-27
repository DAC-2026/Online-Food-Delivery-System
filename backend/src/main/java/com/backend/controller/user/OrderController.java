package com.backend.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.PlaceOrderRequestDto;
import com.backend.service.OrderService;
import com.backend.utils.ApiPath;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.V1)
@RequiredArgsConstructor
@Validated
@CrossOrigin("http://localhost:5173/")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    @Operation(description = "Place a new order")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(request));
    }

    @GetMapping("/users/{userId}/orders")
    @Operation(description = "Get all orders for a user")
    public ResponseEntity<?> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @GetMapping("/orders/{orderId}")
    @Operation(description = "Get order details")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }
}
