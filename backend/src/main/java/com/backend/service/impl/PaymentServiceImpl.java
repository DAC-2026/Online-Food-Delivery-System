package com.backend.service.impl;

import org.json.JSONObject;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.backend.Entity.CustomerOrder;
import com.backend.Repository.CustomerOrderRepository;
import com.backend.constants.PaymentStatus;
import com.backend.dto.PaymentVerificationRequestDto;
import com.backend.exception.ResourceNotFoundException;
import com.backend.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    @Override
    public String createRazorpayOrder(BigDecimal amount) {
        try {
            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);
            
            int amountInPaise = amount.multiply(new BigDecimal(100)).intValue();
            
             if (amountInPaise < 100) {
            	throw new RuntimeException("Order amount " + amount + " is less than minimum allowed (1 INR)");
            }

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amountInPaise); 
            orderRequest.put("currency", "INR");
            // orderRequest.put("receipt", "order_rcptid_" + orderId); // No local order ID yet

            Order razorpayOrder = razorpay.orders.create(orderRequest);
            System.out.print(razorpayOrder);
            return razorpayOrder.get("id");

        } catch (Exception e) {
            throw new RuntimeException("Error creating Razorpay order: " + e.getMessage());
        }
    }

    @Override
    public boolean verifyPaymentSignature(String orderId, String paymentId, String signature) {
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", orderId);
            options.put("razorpay_payment_id", paymentId);
            options.put("razorpay_signature", signature);

            return Utils.verifyPaymentSignature(options, keySecret);
        } catch (Exception e) {
             throw new RuntimeException("Error verifying payment signature: " + e.getMessage());
        }
    }
}
