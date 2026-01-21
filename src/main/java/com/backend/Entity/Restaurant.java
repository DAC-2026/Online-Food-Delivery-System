package com.backend.Entity;
import java.math.BigDecimal;

import com.backend.constants.AvailabilityStatus;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AttributeOverride(name = "id", column = @Column(name = "restaurant_id"))
@Entity
@Table(name = "restaurant")
@Getter
@Setter
@ToString
public class Restaurant extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String name;
    
    @Column(precision = 2, scale = 1)
    private BigDecimal rating;
    
    @Column(length = 500)
    private String description;

    @Column(name = "image_url", length = 2048)
    private String imageUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "availability_status", nullable = false)
    private AvailabilityStatus availabilityStatus;
}
