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

    private long orderDetailSeq;
    private long orderSeq;
    private long productSeq;
    private long sellerSeq;
    private int orderQuantity;
    private String orderTHash;
    private boolean orderToState; //상품 인수 여부
    private String orderDeliveryNumber;
    private String orderDeliveryCompany;

}
