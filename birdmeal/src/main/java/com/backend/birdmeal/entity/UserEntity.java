package com.backend.birdmeal.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="t_user", schema = "birdmeal")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_seq")
    private long userSeq;

    @Basic
    @Column(name="user_email",length = 50,nullable = false)
    private String userEmail;

    @Basic
    @Column(name="user_authority",length = 20,nullable = false)
    private String userAuthority;

    @Basic
    @Column(name="user_nickname",length = 30,nullable = false)
    private String userNickname;

    @Basic
    @Column(name="user_eoa",length = 1024)
    private String userEoa;

    @Basic
    @Column(name="user_tel",length = 20)
    private String userTel;

    @Basic
    @Column(name="user_add",length = 256)
    private String userAdd;

    @Basic
    @Column(name="user_charge_state", columnDefinition = "boolean default false")
    private boolean userChargeState;

    @CreatedDate
    @Basic
    @Column(name = "user_regist_date", length = 30)
    private String userRegistDate;

    @LastModifiedDate
    @Basic
    @Column(name = "user_update_date", length = 30)
    private String userUpdateDate;

}
