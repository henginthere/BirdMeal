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
public class MyOrderDetailResponseDto {
    private long orderDetailSeq;
    private String productName;
    private int productPrice;
    private int orderQuantity;
    private LocalDateTime orderDate;
    private boolean orderToState;
    private String orderDeliveryNumber;
    private String orderDeliveryCompany;
    private String productThumbnailImg;
}
