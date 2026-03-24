package com.electoral.electoral_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "option_set_item")
public class OptionSetItem {
    @Id
    @Column(name = "id", nullable = false)
    private Short id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_option_set")
    private OptionSet optionSet;

    @Column(name = "text", nullable = false, length = 100)
    private String text;

    @Column(name = "value")
    private Short value;

    @Column(name = "display_order")
    private Short displayOrder;
}