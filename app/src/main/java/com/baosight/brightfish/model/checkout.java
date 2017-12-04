package com.baosight.brightfish.model;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/4.
 */

public class checkout extends DataSupport {
    private int id;
    private double price;
    private int amount;
    private String descr;
    private String photo;
    private Goods goods;
    private Buyer buyer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public Goods getGoods() {
        return goods;
    }

    public double getPrice() {
        return price;
    }

    public String getDescr() {
        return descr;
    }

    public String getPhoto() {
        return photo;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }
}
