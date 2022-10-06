package com.backend.birdmeal.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "product_is_deleted=false")
@Table(name = "t_product", schema = "birdmeal", catalog = "")
public class ProductEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_seq")
    private long productSeq;

    @Basic
    @Column(name = "category_seq")
    private long categorySeq;

    @Basic
    @Column(name = "seller_seq")
    private long sellerSeq;

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
    @Column(name = "product_thumbnail_img", length = 1024)
    private String productThumbnailImg;

    @Basic
    @Column(name = "product_description_img", length = 1024)
    private String productDescriptionImg;

    @Basic
    @Column(name = "product_isDeleted",columnDefinition = "boolean default false")
    private boolean productIsDeleted;

    @Basic
    @Column(name = "product_create_date",length = 30)
    private String productCreateDate;

    @Basic
    @Column(name = "product_update_date",length = 30)
    private String productUpdateDate;

    @PrePersist
    public void onPrePersist(){
        this.productCreateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.productUpdateDate = this.productCreateDate;
    }

    @PreUpdate
    private void onPreUpdate(){
        this.productUpdateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

}
