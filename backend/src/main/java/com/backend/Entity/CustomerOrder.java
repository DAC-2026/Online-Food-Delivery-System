package com.backend.Entity;

import java.math.BigDecimal;
import java.util.List;

import com.backend.constants.OrderStatus;
import com.backend.constants.PaymentMode;
import com.backend.constants.PaymentStatus;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@AttributeOverride(name = "id", column = @Column(name = "order_id"))
@Entity
@Table(name = "customer_orders")
public class CustomerOrder extends BaseEntity {

//    @Column(name = "order_number", nullable = false, unique = true)
//    private String orderNumber;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = false)
    private PaymentMode paymentMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    // --- Relationships ---

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address deliveryAddress;

    @OneToMany(
        mappedBy = "order",
        cascade = CascadeType.ALL
    )
    private List<OrderItem> orderItems;
}
