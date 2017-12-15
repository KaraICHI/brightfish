package com.baosight.brightfish.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;
import java.io.Serializable;

public class Checkout extends DataSupport implements Serializable{
    private static final long serialVersionUID=1L;
    private int id;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private int amount;
    private String descr;
    private String photo;
    @Column (nullable = false)
    private int goodsId;
    @Column(nullable = false)
    private int buyerId;
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



    public double getPrice() {
        return price;
    }

    public String getDescr() {
        return descr;
    }

    public String getPhoto() {
        return photo;
    }


    public void setPrice(double price) {
        this.price = price;
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


    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }
}
