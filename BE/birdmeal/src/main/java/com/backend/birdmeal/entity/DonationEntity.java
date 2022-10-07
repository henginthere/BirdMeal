package com.backend.birdmeal.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
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
@Table(name = "t_donation", schema = "birdmeal", catalog = "")
public class DonationEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "donation_seq")
    private long donationSeq;

    @Basic
    @Column(name = "user_seq")
    private long userSeq;

    @Basic
    @Column(name = "donation_price")
    private int donationPrice;

    @Basic
    @Column(name = "donation_date", length = 30)
    private String donationDate;

    @Basic
    @Column(name = "donation_type", columnDefinition = "boolean default false")
    private boolean donationType; //0은 direct, 1은 indirect

    @PrePersist
    public void onPrePersist(){
        this.donationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
