package com.backend.birdmeal.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeatilInfoDto {
    private Long orderDetailSeq;
    private String orderDeliveryNumber;
    private String orderDeliveryCompany;
}
