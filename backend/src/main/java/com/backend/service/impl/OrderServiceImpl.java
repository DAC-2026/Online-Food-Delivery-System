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
import com.backend.dto.OrderDetailsResponse;
import com.backend.dto.OrderItemResponse;
import com.backend.dto.OrderResponse;
import com.backend.dto.PlaceOrderRequestDto;
import com.backend.exception.ResourceNotFoundException;
import com.backend.service.OrderService;

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


    @Override
    public OrderResponse placeOrder(PlaceOrderRequestDto request) {
        
        // 1. Validate Entities
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Address deliveryAddress = addressRepository.findById(request.getDeliveryAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        // 2. Create Order
        CustomerOrder order = new CustomerOrder();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(deliveryAddress);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setPaymentMode(PaymentMode.valueOf(request.getPaymentMode()));

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // 3. Process Items
        for (var itemRequest : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu Item not found"));
            
            // Check availability if needed
             if (!menuItem.getIsAvailable()) {
                 throw new RuntimeException("Item " + menuItem.getName() + " is not available");
             }

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setOrder(order);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(menuItem.getPrice()); // Locking price at order time

            orderItems.add(orderItem);
            
            BigDecimal itemTotal = menuItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        // 4. Save Order
        CustomerOrder savedOrder = orderRepository.save(order);

        // 5. Map to Response
        return mapToOrderResponse(savedOrder);
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
        response.setOrderStatus(order.getOrderStatus().name());
        response.setPaymentStatus(order.getPaymentStatus().name());
        response.setTotalAmount(order.getTotalAmount());
        response.setPaymentMode(order.getPaymentMode().name());
        response.setMessage("Order placed successfully. Proceed to payment.");

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
        response.setOrderStatus(order.getOrderStatus().name());
        response.setPaymentStatus(order.getPaymentStatus().name());
        response.setTotalAmount(order.getTotalAmount());
        response.setPaymentMode(order.getPaymentMode().name());
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
