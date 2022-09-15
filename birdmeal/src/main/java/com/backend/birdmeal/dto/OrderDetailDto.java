package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {

    private Long orderDetailSeq;
    private Long orderSeq;
    private Long productSeq;
    private Long sellerSeq;
    private int orderQuantity;
    private String orderTHash;
    private boolean orderToState; //상품 인수 여부
    private String orderDeliveryNumber;
    private String orderDeliveryCompany;

}
