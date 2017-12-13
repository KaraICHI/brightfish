package com.baosight.brightfish.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/4.
 */

public class Supplier extends DataSupport implements Serializable{
    private static final long serialVersionUID=1L;
    private int id;
    @Column(unique = true,nullable = false)
    private String sku;
    @Column(nullable = false)
    private String name;
    private String address, telephone, cellphone, email, wechat, qq, descr, website, photo;

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

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public String getDescr() {
        return descr;
    }

    public String getAddress() {
        return address;
    }



    public String getEmail() {
        return email;
    }

    public String getQq() {
        return qq;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getWebsite() {
        return website;
    }

    public String getWechat() {
        return wechat;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
