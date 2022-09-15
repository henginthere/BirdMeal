package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarvingChildDto {

    private Long starvingChildSeq;
    private Long userSeq;
    private int childCardNum;

}
