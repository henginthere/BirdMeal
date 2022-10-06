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
public class MyOrderResponseDto {

    private long orderSeq;
    private long userSeq;
    private int orderPrice;
    private String orderDate;
    private String orderFirstName;
    private Long orderCnt;
    private String productThumbnailImg;

}
