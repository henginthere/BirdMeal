package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChildNFTDto {
    private long nftSeq;
    private long userSeq;
    private String nftImg;
    private int nftCnt;
    private String nftCreateDate;
}
