package com.baosight.brightfish.domain;

/**
 * Created by Administrator on 2018/1/21.
 */

public class BuyerData {
    String name;
    int amount;
    double precent;
    double priceSum;
    double priceAvg;
    int amountSum;

    public int getAmountSum() {
        return amountSum;
    }

    public void setAmountSum(int amountSum) {
        this.amountSum = amountSum;
    }

    public double getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(double priceSum) {
        this.priceSum = priceSum;
    }

    public double getPriceAvg() {
        return priceAvg;
    }

    public void setPriceAvg(double priceAvg) {
        this.priceAvg = priceAvg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrecent() {
        return amount*100.00/amountSum;

    }

    public void setPrecent(double precent) {
        this.precent = precent;
    }
}
