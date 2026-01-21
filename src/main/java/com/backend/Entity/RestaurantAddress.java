package com.backend.Entity;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurant_address")
public class RestaurantAddress{
//	@Id
//	@Column(name = "restaurant_id")
//	private Long restaurantId;
    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String pincode;

    @Column(precision = 9, scale = 6, nullable = false)
    private BigDecimal latitude;

    @Column(precision = 9, scale = 6, nullable = false)
    private BigDecimal longitude;

    @Id
    @OneToOne
    @JoinColumn(
        name = "restaurant_id",
        nullable = false,
        unique = true
    )
    private Restaurant restaurant;
}
