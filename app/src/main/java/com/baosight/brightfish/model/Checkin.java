package com.baosight.brightfish.model;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/4.
 */

public class Checkin extends DataSupport {
    private int id;
    private double price;
    private int amount;
    private String descr;
    private String photo;
    private Goods goods;
    private Supplier supplier;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDescr() {
        return descr;
    }

    public double getPrice() {
        return price;
    }

    public Goods getGoods() {
        return goods;
    }

    public int getAmount() {
        return amount;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
