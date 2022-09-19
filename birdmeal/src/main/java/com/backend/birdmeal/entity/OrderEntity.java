package com.backend.birdmeal.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private Long orderSeq;

    @Basic
    @Column(name = "user_seq")
    private Long userSeq;

    @Basic
    @Column(name = "order_price")
    private int orderPrice; //총 가격

    @CreatedDate
    @Column(name = "order_date")
    private LocalDateTime orderDate;

}
