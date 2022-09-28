package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private long productSeq;
    private long categorySeq;
    private long sellerSeq;
    private String sellerName;
    private String productName;
    private int productPrice;
    private String productCa;
    private String productThumbnailImg;
    private String productDescriptionImg;
    private boolean productIsDeleted;
    private String productCreateDate;
    private String productUpdateDate;
}
