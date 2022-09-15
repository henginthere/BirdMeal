package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyOrderResponseDto {

    private Long orderSeq;
    private Long userSeq;
    private int orderPrice;
    private String orderDate;
    private String orderFirstName;
    private Long orderCnt;
    private String productThumbnailImg;

}
