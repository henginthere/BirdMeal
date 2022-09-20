package com.backend.birdmeal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerProductUpdateDto {
    private long productSeq;
    private String sellerEmail;
    private String productName;
    private int productPrice;
    private MultipartFile productThumbnailImg;
    private MultipartFile productDescriptionImg;
}
