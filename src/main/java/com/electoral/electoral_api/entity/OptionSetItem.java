package com.electoral.electoral_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "option_set_item")
public class OptionSetItem {
    @Id
    @Column(name = "id", nullable = false)
    private Short id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_option_set")
    private OptionSet idOptionSet;

    @Column(name = "text", nullable = false, length = 100)
    private String text;

    @Column(name = "value")
    private Short value;

    @Column(name = "display_order")
    private Short displayOrder;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public OptionSet getIdOptionSet() {
        return idOptionSet;
    }

    public void setIdOptionSet(OptionSet idOptionSet) {
        this.idOptionSet = idOptionSet;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }

    public Short getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Short displayOrder) {
        this.displayOrder = displayOrder;
    }

}