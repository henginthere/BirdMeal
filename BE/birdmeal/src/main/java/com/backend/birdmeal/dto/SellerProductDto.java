package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerProductDto {
    private long categorySeq;
    private long sellerSeq;
    private String productName;
    private int productPrice;
    private String productCa;
    private String productThumbnailImg;
    private String productDescriptionImg;
}
