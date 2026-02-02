package com.backend.dto;

import lombok.Data;

@Data
public class PaymentVerificationRequestDto {
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}
