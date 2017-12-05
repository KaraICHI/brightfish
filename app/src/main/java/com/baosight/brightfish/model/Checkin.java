package com.baosight.brightfish.model;

import org.litepal.crud.DataSupport;

import java.sql.Time;
import java.util.Date;

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
    private Date checkinDate;
    private Long checkinTime;

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Long getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Long checkinTime) {
        this.checkinTime = checkinTime;
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
