package com.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenuCategoryRequest {
    private String name;
	@NotNull(message = "description must not be null")
    private String description;
	@NotNull(message = "image url must not be null")
    private String imageUrl;
}
