package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderChildResponseDto {
    private String productName;
    private int productPrice;
    private String productThumbnailImg;
    private int orderQuantity;
    private String orderDate;
}
