package com.backend.birdmeal.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerOrderResponseDto {
    private OrderDto orderDto;
    private List<SellerOrderDetailReaponseDto> sellerOrderDetailReaponseDtoList;
}
