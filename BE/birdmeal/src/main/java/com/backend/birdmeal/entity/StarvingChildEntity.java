package com.backend.birdmeal.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_starving_child", schema = "birdmeal", catalog = "")
public class StarvingChildEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "starving_child_seq")
    private long starvingChildSeq;

    @Basic
    @Column(name="user_email",length = 50)
    private String userEmail;

    @Basic
    @Column(name="child_card_num",length = 50)
    private String childCardNum;

}
