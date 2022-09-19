package com.backend.birdmeal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @JsonIgnore
    // 쓰기 전용 및 조회 불가
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name="user_pass",length = 100)
    private String userPass;

    @Basic
    @Column(name="user_role", columnDefinition = "boolean default false")
    private Boolean userRole;


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

    @Basic
    @Column(name = "user_regist_date", length = 30)
    private String userRegistDate;

    @Basic
    @Column(name = "user_update_date", length = 30)
    private String userUpdateDate;

    @ManyToMany // user와 authority 다대다 관계를 일대다, 다대일 관계의 조인테이블로 정의
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "email", referencedColumnName = "user_email")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<AuthorityEntity> authorities;

    @PrePersist
    public void onPrePersist(){
        this.userRegistDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.userUpdateDate = this.userRegistDate;
    }

    @PreUpdate
    private void onPreUpdate(){
        this.userUpdateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
