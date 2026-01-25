package com.backend.Entity;
import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull; 

import lombok.Data;

@Data
@Entity
@Table(
    name = "menu_item_ratings",
    uniqueConstraints = @UniqueConstraint(columnNames = {"menu_item_id", "user_id"})
)
public class MenuItemRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(precision = 2, scale = 1)
    @NotNull(message = "Rating must not be null")
    @DecimalMin(value = "1.0", message = "Rating must be at least 1" )
    @DecimalMax(value = "5.0", message = "Rating must be at most 5")
    
    private BigDecimal rating; // 1–5
}
