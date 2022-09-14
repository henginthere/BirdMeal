package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SellerDto {

    private long sellerSeq;
    private String sellerEmail;
    private String sellerNickname;
    private String sellerEoa;
    private String sellerTel;
    private String sellerAddress;
    private String sellerInfo;
    private String sellerCreateDate;
    private String sellerUpdateDate;

}
