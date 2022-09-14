package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderDetailDto {

    private Long orderDetailSeq;
    private Long orderSeq;
    private Long productSeq;
    private int orderQuantity;
    private String orderTHash;
    private boolean orderToState; //상품 인수 여부
    private String orderDeliveryNumber;
    private String orderDeliveryCompany;

}
