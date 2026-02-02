package com.backend.service;

import java.math.BigDecimal;

public interface PaymentService {

    String createRazorpayOrder(BigDecimal amount);

    boolean verifyPaymentSignature(String orderId, String paymentId, String signature);

    // void verifyPayment(PaymentVerificationRequestDto request); // Deprecating or modifying this flow
}
