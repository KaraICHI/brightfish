package com.baosight.brightfish.model;

/**
 * Created by saitama on 2017/11/20.
 */
public class HomeItem {
    private int id;
    private int icon;
    private String item_name;
    private int color;

    public HomeItem(int id, int icon, String name) {
        this.id = id;
        this.icon = icon;
        item_name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
