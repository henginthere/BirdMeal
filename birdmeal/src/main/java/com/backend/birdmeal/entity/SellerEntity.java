package com.backend.birdmeal.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_seller", schema = "birdmeal", catalog = "")
public class SellerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="seller_seq")
    private long sellerSeq;

    @Basic
    @Column(name="seller_email",length = 256)
    private String sellerEmail;

    @Basic
    @Column(name="seller_nickname",length = 30)
    private String sellerNickname;

    @Basic
    @Column(name="seller_eoa",length = 1024)
    private String sellerEoa;

    @Basic
    @Column(name="seller_tel",length = 20)
    private String sellerTel;

    @Basic
    @Column(name="seller_address",length = 50)
    private String sellerAddress;

    @Basic
    @Column(name="seller_info",length = 1024)
    private String sellerInfo;

    @Basic
    @Column(name="seller_create_date",length = 30)
    private String sellerCreateDate;

    @Basic
    @Column(name="seller_update_date",length = 30)
    private String sellerUpdateDate;
}
