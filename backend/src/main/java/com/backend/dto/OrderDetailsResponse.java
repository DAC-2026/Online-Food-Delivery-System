package com.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDetailsResponse extends OrderResponse {
    // Current requirement matches OrderResponse, but creating separate class for future extensibility
    // and to match user request.
}
