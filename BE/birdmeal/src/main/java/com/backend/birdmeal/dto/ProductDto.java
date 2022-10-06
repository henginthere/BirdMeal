package com.backend.birdmeal.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private long productSeq;
    private long categorySeq;
    private long sellerSeq;
    private String productName;
    private int productPrice;
    private String productCa;
    private String productThumbnailImg;
    private String productDescriptionImg;
    private boolean productIsDeleted;
    private String productCreateDate;
    private String productUpdateDate;
}
