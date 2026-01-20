package com.backend.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "restaurant_address")
public class RestaurantAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private Long addressId;
	@Column(name = "address_line")
	private String addressLine;
	private String city;
	private String pincode;
	@Column(precision = 9, scale = 6)
	private Double latitude;
	@Column(precision = 9, scale = 6)
	private Double longitude;
	
	@ManyToOne()
	@JoinColumn(name = "restaurant_id", nullable = false)
	private Restaurant restaurant;
//		user_id

	
}
