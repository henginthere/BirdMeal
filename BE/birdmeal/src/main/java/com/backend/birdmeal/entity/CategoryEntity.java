package com.backend.birdmeal.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_category", schema = "birdmeal", catalog = "")
public class CategoryEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "category_seq")
    private long categorySeq;

    @Basic
    @Column(name = "category_name", length = 20)
    private String categoryName;

    @Basic
    @Column(name = "category_icon", length = 256)
    private String categoryIcon;

}
