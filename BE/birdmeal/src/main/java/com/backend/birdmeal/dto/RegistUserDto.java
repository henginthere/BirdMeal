package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistUserDto {

    private String userEmail;
    private boolean userRole; //0이면 일반사용자, 1이면 child
    private String userNickname;

}
