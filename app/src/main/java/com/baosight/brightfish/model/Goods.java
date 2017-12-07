package com.baosight.brightfish.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;



public class Goods extends DataSupport implements Serializable{

    private static final long serialVersionUID=1L;
    private int id;
    @Column(unique = true,nullable = false)
    private String sku;
    @Column(nullable = false)
    private String name;
    private String brand;
    private String catagory;
    private String size;
    private String spec;
    private String descr;
    private String color;
    private String photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public String getCatagory() {
        return catagory;
    }

    public String getDescr() {
        return descr;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSku() {
        return sku;
    }

    public String getSpec() {
        return spec;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }


}
