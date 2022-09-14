package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private long userSeq;
    private String userEmail;
    private String userAuthority;
    private String userEoa;
    private String userTel;
    private String userAddress;
    private boolean userChargeState;
    private String userCreateDate;
    private String userUpdateDate;
}
