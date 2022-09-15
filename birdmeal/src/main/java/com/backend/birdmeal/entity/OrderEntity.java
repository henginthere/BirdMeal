package com.backend.birdmeal.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_order", schema = "birdmeal", catalog = "")
public class OrderEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_seq")
    private Long orderSeq;

    @Basic
    @Column(name = "user_seq")
    private Long userSeq;

    @Basic
    @Column(name = "order_price")
    private int orderPrice; //총 가격

    @Basic
    @Column(name = "order_date", length = 30)
    private String orderDate;

    @Basic
    @Column(name = "seller_seq")
    private Long sellerSeq;

}
