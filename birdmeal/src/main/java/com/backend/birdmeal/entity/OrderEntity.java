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
@Table(name = "t_order", schema = "birdmeal", catalog = "")
public class OrderEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_seq")
    private long orderSeq;

    @Basic
    @Column(name = "user_seq")
    private long userSeq;

    @Basic
    @Column(name = "order_price")
    private int orderPrice; //총 가격

    @Basic
    @Column(name = "order_date",length = 30)
    private String orderDate;

    @PrePersist
    public void onPrePersist(){
        this.orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
