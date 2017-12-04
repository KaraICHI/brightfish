package com.baosight.brightfish.model;

import com.baosight.brightfish.ChooseSet;

/**
 * Created by Administrator on 2017/12/4.
 */

public class ChooseItem {
    private String name;
    private String sku;

    public ChooseItem(String name,String sku){
        this.name=name;
        this.sku=sku;
    }

    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
