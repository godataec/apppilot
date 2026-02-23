package com.bancointernacional.plataformaBI.models.entities;

import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COLORPALETTE")
@Where(clause = "ISDELETED = False")
public class ColorPalette {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "COLORNAME")
    private String color;

    @Column(name = "HEXVALUE")
    private String value;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ISDELETED")
    private boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
