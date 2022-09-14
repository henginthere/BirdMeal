package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {
    private Long productSeq;
    private Long categorySeq;
    private Long sellerSeq;
    private String productName;
    private int productPrice;
    private String productCa;
    private String productThumbnailImg;
    private String productDescriptionImg;
    private boolean productIsDeleted;
    private String productCreateDate;
    private String productUpdateDate;
}
