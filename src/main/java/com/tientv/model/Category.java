package com.tientv.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "category")
    private Set<com.tientv.model.Product> products = new LinkedHashSet<>();

}