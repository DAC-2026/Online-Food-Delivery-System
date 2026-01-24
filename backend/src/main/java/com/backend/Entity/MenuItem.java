package com.backend.Entity;
import java.math.BigDecimal;

import jakarta.persistence.AttributeOverride;

//import com.backend.constants.AvailabilityStatus;

//import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
//import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "menu_item")
@AttributeOverride(name = "id", column = @Column(name = "item_id"))
@Getter
@Setter
@ToString(exclude = "category")
public class MenuItem extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean isVeg;

    @Column(nullable = false)
    private Boolean isAvailable;

    private Integer preparationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private MenuCategory category;
}
