package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
