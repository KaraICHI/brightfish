package com.baosight.brightfish.Dao;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.baosight.brightfish.domain.Checkout;
import com.baosight.brightfish.domain.Buyer;
import com.baosight.brightfish.domain.BuyerData;
import com.baosight.brightfish.ui.goods.ListAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/1/21.
 */

public class CheckoutDao {
    public static int sumAllCheckoutAmountByGoodsId(int goodsId) {
        int amount= DataSupport.where("goodsId=?", goodsId + "").sum(Checkout.class, "amount", Integer.class);
        return amount;
    }

    public static double sumAllCheckoutMoneyByGoodsId(int goodsId) {
        Cursor cursor = DataSupport.findBySQL("select sum(price*amount) as a from checkout where goodsId=?", goodsId + "");
        // return cursor.getDouble(0);
        return 13424.22;
    }

    public static List<Buyer> getAllCheckoutBuyerByGoodsId(int goodsId) {
        Cursor cursor = DataSupport.findBySQL("select buyerId as s from checkout where goodsId=?", goodsId + "");

        List<Buyer> buyers = new ArrayList<>();
        a:  while (cursor.moveToNext()) {
            int buyerId = cursor.getInt(cursor.getColumnIndex("s"));
            Buyer buyer = DataSupport.find(Buyer.class, buyerId);
            int len = buyers.size();
            for (int i = 0; i < len; i++) {
                if (buyers.get(i).getId() == buyerId) {
                    continue a;
                }
            }
            buyers.add(buyer);

        }
        return buyers;
    }

    public static Set<BuyerData> getBuyerDataByGoodsId(int goodsId) {
        Set<BuyerData> buyerDataSet = new HashSet<>();
        List<Buyer> buyers = getAllCheckoutBuyerByGoodsId(goodsId);
        for (Buyer s : buyers) {
            BuyerData buyerData = new BuyerData();
            buyerData.setName(s.getName());
            int amount = DataSupport.where("buyerId=? and goodsId=?", s.getId() + "",goodsId+"").sum(Checkout.class, "amount", Integer.class);
            buyerData.setAmount(amount);
            int sumAmount = sumAllCheckoutAmountByGoodsId(goodsId);
            double precent = amount / sumAmount;
            buyerData.setPrecent(precent);
            double priceSum = DataSupport.where("buyerId=? and goodsId=?", s.getId() + "",goodsId+"").sum(Checkout.class, "price", Integer.class);
            buyerData.setPriceSum(priceSum);
            double priceAvg = DataSupport.where("buyerId=? and goodsId=?", s.getId() + "",goodsId+"").average(Checkout.class, "price");
            buyerData.setPriceAvg(priceAvg);
            buyerData.setAmountSum(sumAllCheckoutAmountByGoodsId(goodsId));
            buyerDataSet.add(buyerData);
        }
        return buyerDataSet;
    }

}
