package com.baosight.brightfish.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/8.
 */

public class Checklist extends DataSupport implements Serializable {
    private static final long serialVersionUID=1L;
    private int id;
    @Column (unique = true,nullable = false)
    private int goodsId;
    @Column (nullable = false)
    private int amount;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
