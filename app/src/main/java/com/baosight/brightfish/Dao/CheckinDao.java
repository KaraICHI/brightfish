package com.baosight.brightfish.Dao;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.baosight.brightfish.domain.Checkin;
import com.baosight.brightfish.domain.Supplier;
import com.baosight.brightfish.domain.SupplierData;
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

public class CheckinDao {
    public static int sumAllCheckinAmountByGoodsId(int goodsId) {
        int amount= DataSupport.where("goodsId=?", goodsId + "").sum(Checkin.class, "amount", Integer.class);
        return amount;
    }

    public static double sumAllCheckinMoneyByGoodsId(int goodsId) {
        Cursor cursor = DataSupport.findBySQL("select sum(price*amount) as a from checkin where goodsId=?", goodsId + "");
        // return cursor.getDouble(0);
        return 13424.22;
    }

    public static List<Supplier> getAllCheckinSupplierByGoodsId(int goodsId) {
        Cursor cursor = DataSupport.findBySQL("select supplierId as s from checkin where goodsId=?", goodsId + "");

        List<Supplier> suppliers = new ArrayList<>();
      a:  while (cursor.moveToNext()) {
            int supplierId = cursor.getInt(cursor.getColumnIndex("s"));
            Supplier supplier = DataSupport.find(Supplier.class, supplierId);
            int len = suppliers.size();
            for (int i = 0; i < len; i++) {
                if (suppliers.get(i).getId() == supplierId) {
                    continue a;
                }
            }
            suppliers.add(supplier);

        }
        return suppliers;
    }

    public static Set<SupplierData> getSupplierDataByGoodsId(int goodsId) {
        Set<SupplierData> supplierDataSet = new HashSet<>();
        List<Supplier> suppliers = getAllCheckinSupplierByGoodsId(goodsId);
        for (Supplier s : suppliers) {
            SupplierData supplierData = new SupplierData();
            supplierData.setName(s.getName());
            int amount = DataSupport.where("supplierId=? and goodsId=?", s.getId() + "",goodsId+"").sum(Checkin.class, "amount", Integer.class);
            supplierData.setAmount(amount);
            int sumAmount = sumAllCheckinAmountByGoodsId(goodsId);
            double precent = amount / sumAmount;
            supplierData.setPrecent(precent);
            double priceSum = DataSupport.where("supplierId=? and goodsId=?", s.getId() + "",goodsId+"").sum(Checkin.class, "price", Integer.class);
            supplierData.setPriceSum(priceSum);
            double priceAvg = DataSupport.where("supplierId=? and goodsId=?", s.getId() + "",goodsId+"").average(Checkin.class, "price");
            supplierData.setPriceAvg(priceAvg);
            supplierData.setAmountSum(sumAllCheckinAmountByGoodsId(goodsId));
            supplierDataSet.add(supplierData);
        }
        return supplierDataSet;
    }

}
