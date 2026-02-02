package com.backend.dto;
import java.util.List;

import com.backend.constants.PaymentMode;

import lombok.Data;

@Data
public class PlaceOrderRequestDto {
	private Long userId; // Assuming we might need to pass userId if not from context

	private Long deliveryAddressId;
	private PaymentMode paymentMode;
    
    // Payment Details
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;

	private List<OrderItemRequestDto> items;
}
