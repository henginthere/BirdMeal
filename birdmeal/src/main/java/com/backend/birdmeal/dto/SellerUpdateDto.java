package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerUpdateDto {

    private long sellerSeq;
    private String sellerNickname;
    private String sellerTel;
    private String sellerAddress;
    private String sellerInfo;

}
