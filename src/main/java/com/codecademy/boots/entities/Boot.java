package com.codecademy.boots.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import com.codecademy.boots.enums.BootType;

@Entity
@Table(name = "BOOTS")
public class Boot {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private BootType type;

    @Column(name = "SIZE")
    private Float size;

    @Column(name = "MATERIAL")
    private String material;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "BESTSELLER")
    private Boolean bestSeller;

    public Boot() {
    }

    public Boot(BootType type, Float size, String material, Integer quantity, Boolean bestSeller) {
        this.type = type;
        this.size = size;
        this.material = material;
        this.quantity = quantity;
        this.bestSeller = bestSeller;
    }

    public BootType getType() {
        return type;
    }

    public void setType(BootType type) {
        this.type = type;
    }

    public Boolean getBestSeller() {
        return bestSeller;
    }

    public void setBestSeller(Boolean bestSeller) {
        this.bestSeller = bestSeller;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getSize() {
        return this.size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public String getMaterial() {
        return this.material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}