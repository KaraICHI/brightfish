package com.baosight.brightfish.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/4.
 */

public class Checkin extends DataSupport implements Serializable{
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
    private int supplierId;
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

    public String getPhoto() {
        return photo;
    }

    public String getDescr() {
        return descr;
    }

    public double getPrice() {
        return price;
    }



    public int getAmount() {
        return amount;
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



    public void setPrice(double price) {
        this.price = price;
    }


    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
}
