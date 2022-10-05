package com.backend.birdmeal.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_order_detail", schema = "birdmeal", catalog = "")
public class OrderDetailEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_detail_seq")
    private long orderDetailSeq;

    @Basic
    @Column(name = "order_seq")
    private long orderSeq;

    @Basic
    @Column(name = "category_seq")
    private long categorySeq;

    @Basic
    @Column(name = "seller_seq")
    private long sellerSeq;

    @Basic
    @Column(name = "order_quantity")
    private int orderQuantity;

    @Basic
    @Column(name = "order_t_hash",length = 1024)
    private String orderTHash;

    @Basic
    @Column(name = "productCa",length = 256)
    private String productCa;

    @Basic
    @Column(name = "order_to_state", columnDefinition = "boolean default false")
    private boolean orderToState; //상품 인수 여부

    @Basic
    @Column(name = "order_delivery_number",length = 64)
    private String orderDeliveryNumber;

    @Basic
    @Column(name = "order_delivery_company",length = 32)
    private String orderDeliveryCompany;

    @Basic
    @Column(name = "product_name", length = 256)
    private String productName;

    @Basic
    @Column(name = "product_price")
    private int productPrice;

    @Basic
    @Column(name = "product_thumbnail_img", length = 1024)
    private String productThumbnailImg;

    @Basic
    @Column(name = "product_is_deleted", columnDefinition = "boolean default false")
    private boolean productIsDeleted; //상품 삭제 여부

    @Basic
    @Column(name = "order_is_canceled", columnDefinition = "boolean default false")
    private boolean orderIsCanceled; //주문 취소 여부

    @Basic
    @Column(name = "order_is_refunded", columnDefinition = "boolean default false")
    private boolean orderIsRefunded; // 주문 환불 여부

}
