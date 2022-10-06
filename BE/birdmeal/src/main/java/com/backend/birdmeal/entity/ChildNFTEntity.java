package com.backend.birdmeal.entity;

import lombok.*;
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
@Table(name = "t_child_nft", schema = "birdmeal", catalog = "")
public class ChildNFTEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "nft_seq")
    private long nftSeq;

    @Basic
    @Column(name = "user_seq")
    private long userSeq;

    @Basic
    @Column(name = "nft_img")
    private String nftImg;

    @Basic
    @Column(name = "nft_cnt")
    private int nftCnt;

    @Basic
    @Column(name = "nft_create_date", length = 30)
    private String nftCreateDate;

    @PrePersist
    public void onPrePersist(){
        this.nftCreateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
