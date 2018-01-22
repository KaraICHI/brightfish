package com.baosight.brightfish.Dao;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.baosight.brightfish.domain.Checkin;
import com.baosight.brightfish.domain.Supplier;
import com.baosight.brightfish.domain.SupplierData;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/1/21.
 */

public class CheckinDao {
    public static int sumAllCheckinAmountByGoodsId(int goodsId){
        return DataSupport.where("goodsId=?",goodsId+"").sum(Checkin.class,"amount",Integer.class);
    }
    public static double sumAllCheckinMoneyByGoodsId(int goodsId){
        Cursor cursor=DataSupport.findBySQL("select sum(price*amount) as a from checkin where goodsId=?",goodsId+"");
       // return cursor.getDouble(0);
        return 13424.22;
    }
    public static Set<Supplier> getAllCheckinSupplierByGoodsId(int goodsId){
        Cursor cursor=DataSupport.findBySQL("select supplierId as s from checkin where goodsId=?",goodsId+"");
        Set<Supplier> suppliers=new HashSet<>();
        if(cursor!=null&&cursor.moveToFirst()){
            do {
              int supplierId=cursor.getInt(cursor.getColumnIndex("s"));
              Supplier supplier=DataSupport.find(Supplier.class,supplierId);
              suppliers.add(supplier);
            }while (cursor.moveToNext());

        }
        return suppliers;
    }
    public static Set<SupplierData> getSupplierDataByGoodsId(int goodsId){
        Set<SupplierData> supplierDataSet=new HashSet<>();
        Set<Supplier> suppliers=getAllCheckinSupplierByGoodsId(goodsId);
        for (Supplier s:suppliers) {
            SupplierData supplierData=new SupplierData();
            supplierData.setName(s.getName());
            int amount=DataSupport.where("supplierId=?",s.getId()+"").sum(Checkin.class,"amount",Integer.class);
            supplierData.setAmount(amount);
            int sumAmount=sumAllCheckinAmountByGoodsId(goodsId);
            double precent=amount*1.00/sumAmount;
            supplierData.setPrecent(precent);
            double priceSum= DataSupport.where("supplierId=?",s.getId()+"").sum(Checkin.class,"price",Integer.class);
            supplierData.setPriceSum(priceSum);
            double priceAvg=DataSupport.where("supplierId=?",s.getId()+"").average(Checkin.class,"price");
            supplierData.setPriceAvg(priceAvg);
            supplierDataSet.add(supplierData);
        }
      return supplierDataSet;
    }

}
