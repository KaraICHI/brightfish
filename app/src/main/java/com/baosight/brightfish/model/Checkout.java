package com.baosight.brightfish.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by Administrator on 2017/12/4.
 */

public class Checkout extends DataSupport {
    private int id;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private int amount;
    private String descr;
    private String photo;
    @Column (nullable = false,unique = true)
    private Goods goods;
    @Column(nullable = false)
    private Buyer buyer;
    @Column(nullable = false)
    private String checkinDate;

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }


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
