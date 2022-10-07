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
    private long orderSeq;
    private long userSeq;
    private String userNickname;
    private String userTel;
    private String userAdd;
    private String userAddDetail; // 상세주소
    private int orderPrice;
    private long orderDetailSeq;
    private long productSeq;
    private long sellerSeq;
    private String orderDate;
    private int orderQuantity;
    private String orderTHash;
    private boolean orderToState; //상품 인수 여부
    private String orderDeliveryNumber;
    private String orderDeliveryCompany;
    private String categoryName;
    private String productName;
    private int productPrice;
    private String productCa;
    private String productThumbnailImg;
    private String productDescriptionImg;
    private boolean productIsDeleted;
    private boolean orderIsCanceled;
    private boolean orderIsRefunded;
    private String productCreateDate;
    private String productUpdateDate;
}
