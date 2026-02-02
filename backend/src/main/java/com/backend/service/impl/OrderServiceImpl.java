package com.backend.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.backend.Application;
import com.backend.Entity.Address;
import com.backend.Entity.CustomerOrder;
import com.backend.Entity.MenuItem;
import com.backend.Entity.OrderItem;
import com.backend.Entity.Restaurant;
import com.backend.Entity.User;
import com.backend.Repository.AddressRepository;
import com.backend.Repository.CustomerOrderRepository;
import com.backend.Repository.MenuItemRepository;
import com.backend.Repository.RestaurantRepository;
import com.backend.Repository.UserRepository;
import com.backend.constants.OrderStatus;
import com.backend.constants.PaymentMode;
import com.backend.constants.PaymentStatus;
import com.backend.dto.MenuItemDto;
import com.backend.dto.OrderDetailsResponse;
import com.backend.dto.OrderItemRequestDto;
import com.backend.dto.OrderItemResponse;
import com.backend.dto.OrderResponse;
import com.backend.dto.PlaceOrderRequestDto;
import com.backend.exception.ResourceNotFoundException;
import com.backend.service.OrderService;
import com.backend.service.PaymentService;

import jakarta.transaction.Transactional; 
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Application application;

    private final CustomerOrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final MenuItemRepository menuItemRepository;
    private final PaymentService paymentService;


    @Override
    public OrderResponse placeOrder(PlaceOrderRequestDto request) {
        
        // 1. Calculate and Validate Amount
        BigDecimal totalAmount = calculateTotalOrderAmount(request);
        
        // 2. Validate Minimum Amount
        if (totalAmount.compareTo(BigDecimal.ONE) < 0) {
             throw new RuntimeException("Order total amount " + totalAmount + " is less than minimum allowed (1 INR).");
        }
        
        // 3. Verify Payment if Online (Anything other than COD)
        if (request.getPaymentMode() != PaymentMode.COD) {
            String rnpOrderId = request.getRazorpayOrderId();
            String rnpPaymentId = request.getRazorpayPaymentId();
            String rnpSignature = request.getRazorpaySignature();
            
            if (rnpOrderId == null || rnpPaymentId == null || rnpSignature == null) {
                throw new RuntimeException("Missing payment details for Online order.");
            }
            
            boolean isSignatureValid = paymentService.verifyPaymentSignature(rnpOrderId, rnpPaymentId, rnpSignature);
            
            if (!isSignatureValid) {
                 throw new RuntimeException("Payment verification failed. invalid signature.");
            }
        }

        // 4. Validate Entities (User, Address)
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Address deliveryAddress = addressRepository.findById(request.getDeliveryAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        // 5. Create Order
        CustomerOrder order = new CustomerOrder();
        order.setUser(user);
        order.setDeliveryAddress(deliveryAddress);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        
        if (request.getPaymentMode() != PaymentMode.COD) {
             order.setPaymentStatus(PaymentStatus.COMPLETED);
             order.setRazorpayOrderId(request.getRazorpayOrderId());
        } else {
             order.setPaymentStatus(PaymentStatus.PENDING);
        }
        
        order.setPaymentMode(request.getPaymentMode());

        List<OrderItem> orderItems = new ArrayList<>();
        // Re-calculating items for saving (could optimize to reuse from step 1 but keeping separate for now to match flow)
        BigDecimal finalTotal = BigDecimal.ZERO; 

        for (var itemRequest : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu Item not found"));
            
            if (!menuItem.getIsAvailable()) {
                 throw new RuntimeException("Item " + menuItem.getName() + " is not available");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setOrder(order);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(menuItem.getPrice()); 

            orderItems.add(orderItem);
            
            BigDecimal itemTotal = menuItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            finalTotal = finalTotal.add(itemTotal);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(finalTotal);

        // 6. Save Order
        CustomerOrder savedOrder = orderRepository.save(order);

        // 7. Map to Response
        return mapToOrderResponse(savedOrder);
    }
    
    @Override
    public BigDecimal calculateTotalOrderAmount(PlaceOrderRequestDto request) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequestDto itemRequest : request.getItems()) {
             MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu Item not found"));
             
             BigDecimal itemTotal = menuItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
             total = total.add(itemTotal);
        }
        return total;
    }

    @Override
    public List<OrderResponse> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        List<CustomerOrder> orders = orderRepository.findAllByUser(user);
        
        return orders.stream()
        		.map(this::mapToOrderResponse)
//                .map(order -> mapToOrderResponse(order))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailsResponse getOrderDetails(Long orderId) {
        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        
        return mapToOrderDetailsResponse(order);
    }

    private OrderResponse mapToOrderResponse(CustomerOrder order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setOrderStatus(order.getOrderStatus());
        response.setPaymentStatus(order.getPaymentStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setPaymentMode(order.getPaymentMode());
        response.setMessage("Order placed successfully.");

        List<OrderItemResponse> itemResponses = order.getOrderItems().stream().map(item -> {
            OrderItemResponse itemResponse = new OrderItemResponse();
            itemResponse.setMenuItemName(item.getMenuItem().getName());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setPrice(item.getPrice());
            return itemResponse;
        }).collect(Collectors.toList());

        response.setItems(itemResponses);
        return response;
    }

    private OrderDetailsResponse mapToOrderDetailsResponse(CustomerOrder order) {
        OrderDetailsResponse response = new OrderDetailsResponse();
        // Copy properties from regular response mapping or duplicate logic
        // Since OrderDetailsResponse extends OrderResponse, we can populate it similarly
        response.setOrderId(order.getId());
        response.setOrderStatus(order.getOrderStatus());
        response.setPaymentStatus(order.getPaymentStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setPaymentMode(order.getPaymentMode());
        response.setMessage("Order details fetched successfully."); // Custom message

        List<OrderItemResponse> itemResponses = order.getOrderItems().stream().map(item -> {
            OrderItemResponse itemResponse = new OrderItemResponse();
            itemResponse.setMenuItemName(item.getMenuItem().getName());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setPrice(item.getPrice());
            return itemResponse;
        }).collect(Collectors.toList());

        response.setItems(itemResponses);
        return response;
    }
}
