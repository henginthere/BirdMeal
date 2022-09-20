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
public class SellerProductDto {
    private long categorySeq;
    private long sellerSeq;
    private String productName;
    private int productPrice;
    private String productCa;
    private MultipartFile productThumbnailImg;
    private MultipartFile productDescriptionImg;
}
