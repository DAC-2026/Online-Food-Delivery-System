package com.backend.dto;

import lombok.Data;

@Data
public class MenuCategoryResponse {
    private Long categoryId;
    private String name;
    private String description;
    private String imageUrl;
}
