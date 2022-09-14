package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DonationHistoryDto {
    private Long donationHistorySeq;
    private Long userSeq;
    private int donationPrice;
    private String donationDate;
    private boolean donationType;
}
