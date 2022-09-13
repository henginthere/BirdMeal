package com.backend.birdmeal.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private long userSeq;
    private String userEmail;
    private String userNickname;
    private String userEoa;
    private String userTel;
    private String userAdd;
    private boolean userChargeState;
    private String userRegistDate;
    private String userUpdateDate;
}
