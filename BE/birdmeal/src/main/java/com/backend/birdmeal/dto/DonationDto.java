package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationDto {
    private long donationSeq;
    private long userSeq;
    private int donationPrice;
    private String donationDate;
    private boolean donationType;
}
