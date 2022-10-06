package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponseDto {
    private long orderDetailSeq;
    private String orderTHash;
    private int orderQuantity;
    private int productPrice;
    private String productCa;
}
