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
    private Long starvingChildSeq;

    @Basic
    @Column(name="user_seq")
    private Long userSeq;

    @Basic
    @Column(name="child_card_num")
    private Long childCardNum;

}
