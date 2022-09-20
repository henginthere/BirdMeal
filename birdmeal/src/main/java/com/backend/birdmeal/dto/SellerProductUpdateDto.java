package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerProductUpdateDto {
    private long productSeq;
    private int productPrice;
    private String productThumbnailImg;
    private String productDescriptionImg;
}
