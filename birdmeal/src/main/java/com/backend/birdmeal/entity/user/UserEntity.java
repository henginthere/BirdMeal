package com.backend.birdmeal.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
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
    @Column(name="user_email",length = 50)
    private String userEmail;

    @Basic
    @Column(name="user_nickname",length = 30)
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
    @Column(name = "user_regist_date")
    private String userRegistDate;

    @LastModifiedDate
    @Basic
    @Column(name = "user_update_date")
    private String userUpdateDate;


    // 회원 권한
    @ManyToMany // user와 authority 다대다 관계를 일대다, 다대일 관계의 조인테이블로 정의
    @JoinTable(
            name = "t_user_authority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "user_seq")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

}
