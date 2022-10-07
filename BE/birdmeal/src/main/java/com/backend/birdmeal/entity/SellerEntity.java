package com.backend.birdmeal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name="seller_tel",length = 20)
    private String sellerTel;

    @Basic
    @Column(name="seller_address",length = 50)
    private String sellerAddress;

    @Basic
    @Column(name="seller_info",length = 1024)
    private String sellerInfo;

    @Basic
    @Column(name="seller_img",length = 1024)
    private String sellerImg;

    @Basic
    @Column(name="seller_create_date", length = 30)
    private String sellerCreateDate;

    @Basic
    @Column(name="seller_update_date", length = 30)
    private String sellerUpdateDate;



    @Basic
    @JsonIgnore
    // 쓰기 전용 및 조회 불가
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name="seller_pass",length = 100)
    private String sellerPass;

    @PrePersist
    public void onPrePersist(){
        this.sellerCreateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.sellerUpdateDate = this.sellerCreateDate;
    }

    @PreUpdate
    private void onPreUpdate(){
        this.sellerUpdateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

}
