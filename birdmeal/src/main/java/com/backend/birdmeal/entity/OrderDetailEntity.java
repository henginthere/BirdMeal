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
    private Long orderDetailSeq;

    @Basic
    @Column(name = "order_seq")
    private Long orderSeq;

    @Basic
    @Column(name = "product_seq")
    private Long productSeq;

    @Basic
    @Column(name = "order_quantity")
    private int orderQuantity;

    @Basic
    @Column(name = "order_t_hash",length = 1024)
    private String orderTHash;

    @Basic
    @Column(name = "order_to_state", columnDefinition = "boolean default false")
    private boolean orderToState; //상품 인수 여부

    @Basic
    @Column(name = "order_delivery_number",length = 64)
    private String orderDeliveryNumber;

    @Basic
    @Column(name = "order_delivery_company",length = 32)
    private String orderDeliveryCompany;

}
