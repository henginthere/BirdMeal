package com.backend.birdmeal.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private long userSeq;
    private int orderQuantity;
    private String orderTHash;
    private long productSeq;
}
