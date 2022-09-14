package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationHistoryDto {
    private Long donationHistorySeq;
    private Long userSeq;
    private int donationPrice;
    private String donationDate;
    private boolean donationType;
}
