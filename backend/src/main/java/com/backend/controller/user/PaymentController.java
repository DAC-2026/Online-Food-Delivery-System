package com.backend.controller.user;

import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.PaymentVerificationRequestDto;
import com.backend.service.PaymentService;
import com.backend.utils.ApiPath;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import com.backend.dto.PlaceOrderRequestDto;
import com.backend.service.OrderService;

@RestController
@RequestMapping(ApiPath.V1 + "/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @PostMapping("/create-order")
    @Operation(description = "Create Razorpay Order")
    public ResponseEntity<?> createRazorpayOrder(@RequestBody PlaceOrderRequestDto request) {
        BigDecimal totalAmount = orderService.calculateTotalOrderAmount(request);
        String razorpayOrderId = paymentService.createRazorpayOrder(totalAmount);
        return ResponseEntity.ok(razorpayOrderId);
    }
}
