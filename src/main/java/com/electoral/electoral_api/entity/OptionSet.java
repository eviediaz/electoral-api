package com.electoral.electoral_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "option_set")
public class OptionSet {
    @Id
    @Column(name = "id", nullable = false)
    private Short id;

    @Column(name = "name", length = 50)
    private String name;
}