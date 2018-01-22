package com.baosight.brightfish.Dao;

import android.database.Cursor;

import com.baosight.brightfish.domain.Checkout;
import com.baosight.brightfish.domain.Buyer;
import com.baosight.brightfish.domain.BuyerData;

import org.litepal.crud.DataSupport;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/1/21.
 */

public class CheckoutDao {
    public static int sumAllCheckoutAmountByGoodsId(int goodsId){
        return DataSupport.where("goodsId=?",goodsId+"").sum(Checkout.class,"amount",Integer.class);
    }
    public static double sumAllCheckoutMoneyByGoodsId(int goodsId){
        Cursor cursor=DataSupport.findBySQL("select sum(amount*price) as a from checkout where goodsId=?",goodsId+"");
       // return cursor.getDouble(cursor.getColumnIndex("all"));
        return 1222.21;
    }
    public static Set<Buyer> getAllCheckoutBuyerByGoodsId(int goodsId){
        Cursor cursor=DataSupport.findBySQL("select buyerId as b from checkout where goodsId=?",goodsId+"");
        Set<Buyer> Buyers=new HashSet<>();
        if(cursor!=null&&cursor.moveToFirst()){
            do {
                int buyerId=cursor.getInt(cursor.getColumnIndex("b"));
                Buyer Buyer=DataSupport.find(Buyer.class,buyerId);
                Buyers.add(Buyer);
            }while (cursor.moveToNext());

        }
        return Buyers;
    }
    public static Set<BuyerData> getBuyerDataByGoodsId(int goodsId){
        Set<BuyerData> BuyerDataSet=new HashSet<>();
        Set<Buyer> Buyers=getAllCheckoutBuyerByGoodsId(goodsId);
        for (Buyer s:Buyers) {
            BuyerData BuyerData=new BuyerData();
            BuyerData.setName(s.getName());
            int amount=DataSupport.where("buyerId=?",s.getId()+"").sum(Checkout.class,"amount",Integer.class);
            BuyerData.setAmount(amount);
            int sumAmount=sumAllCheckoutAmountByGoodsId(goodsId);
            double precent=amount*1.00/sumAmount;
            BuyerData.setPrecent(precent);
            double priceSum= DataSupport.where("buyerId=?",s.getId()+"").sum(Checkout.class,"price",Integer.class);
            BuyerData.setPriceSum(priceSum);
            double priceAvg=DataSupport.where("buyerId=?",s.getId()+"").average(Checkout.class,"price");
            BuyerData.setPriceAvg(priceAvg);
            BuyerDataSet.add(BuyerData);
        }
        return BuyerDataSet;
    }
}
