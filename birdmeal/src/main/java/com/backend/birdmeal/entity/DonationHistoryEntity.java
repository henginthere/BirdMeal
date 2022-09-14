package com.backend.birdmeal.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_donation_history", schema = "birdmeal", catalog = "")
public class DonationHistoryEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "donation_seq")
    private Long donationHistorySeq;

    @Basic
    @Column(name = "user_seq")
    private Long userSeq;

    @Basic
    @Column(name = "donation_price")
    private int donationPrice;

    @Basic
    @Column(name = "donation_date", length = 30)
    private String donationDate;

    @Basic
    @Column(name = "donation_type", columnDefinition = "boolean default false")
    private boolean donationType; //0은 direct, 1은 indirect
}
