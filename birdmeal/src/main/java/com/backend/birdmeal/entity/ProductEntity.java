package com.backend.birdmeal.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_product", schema = "birdmeal", catalog = "")
public class ProductEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_seq")
    private Long productSeq;

    @Basic
    @Column(name = "category_seq")
    private Long categorySeq;

    @Basic
    @Column(name = "seller_seq")
    private Long sellerSeq;

    @Basic
    @Column(name = "product_name", length = 256)
    private String productName;

    @Basic
    @Column(name = "product_price")
    private int productPrice;

    @Basic
    @Column(name = "product_ca", length = 256)
    private String productCa;

    @Basic
    @Column(name = "product_thumbnail_img", length = 256)
    private String productThumbnailImg;

    @Basic
    @Column(name = "product_description_img", length = 256)
    private String productDescriptionImg;

    @Basic
    @Column(name = "product_isDeleted",columnDefinition = "boolean default false")
    private boolean productIsDeleted;

    @Basic
    @Column(name = "product_create_date", length = 30)
    private String productCreateDate;

    @Basic
    @Column(name = "product_update_date", length = 30)
    private String productUpdateDate;

}
