package com.backend.dto;
import java.util.List;
import lombok.Data;

@Data
public class PlaceOrderRequestDto {
	private Long userId; // Assuming we might need to pass userId if not from context
	private Long restaurantId;
	private Long deliveryAddressId;
	private String paymentMode;
	private List<OrderItemRequestDto> items;
}
