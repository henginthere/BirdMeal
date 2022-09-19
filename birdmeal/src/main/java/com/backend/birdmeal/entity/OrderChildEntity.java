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
@Table(name = "t_order_child", schema = "birdmeal", catalog = "")
public class OrderChildEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_child_detail_seq")
    private long orderChildDetailSeq;

    @Basic
    @Column(name = "user_seq")
    private long userSeq;

    @Basic
    @Column(name = "user_nickname")
    private String userNickname;

    @Basic
    @Column(name = "order_date")
    private String orderDate;

    @Basic
    @Column(name = "order_quantity")
    private int orderQuantity;

    @Basic
    @Column(name = "product_name")
    private String productName;

    @Basic
    @Column(name = "product_price")
    private int productPrice;

    @Basic
    @Column(name = "product_thumbnail_img")
    private String productThumbnailImg;

    @PrePersist
    public void onPrePersist(){
        this.orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

}
