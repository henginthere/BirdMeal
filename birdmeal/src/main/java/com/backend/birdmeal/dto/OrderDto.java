package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderDto {

    private Long orderSeq;
    private Long userSeq;
    private int orderPrice; //총 가격
    private String orderDate;

}
