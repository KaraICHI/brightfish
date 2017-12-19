package com.baosight.brightfish;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baosight.brightfish.model.Buyer;
import com.baosight.brightfish.model.Checkin;
import com.baosight.brightfish.model.Checklist;
import com.baosight.brightfish.model.Checkout;
import com.baosight.brightfish.model.Goods;
import com.baosight.brightfish.model.Supplier;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/12/7.
 */

public class BasicActivity extends AppCompatActivity {
    RelativeLayout currentSortMethod;
    boolean sortdesc;
    Toolbar toolbar;


    protected void initToolbar(int color) {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setBackgroundColor(getResources().getColor(color));
    }
    protected SwipeMenuCreator getSlideMenuCreator(){


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                openItem.setWidth(240);
                openItem.setTitle("Open");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(240);
                deleteItem.setTitle("Delete");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        return creator;
    }

    protected void deleteGoods(int goodsId){
        DataSupport.delete(Goods.class,goodsId);
        DataSupport.deleteAll(Checkin.class,"goodsId=?",goodsId+"");
        DataSupport.deleteAll(Checkout.class,"goodsId=?",goodsId+"");
        DataSupport.deleteAll(Checklist.class,"goodsId=?",goodsId+"");
    }

    protected void deleteBuyer(int buyerId){
        DataSupport.delete(Buyer.class,buyerId);
        DataSupport.deleteAll(Checkout.class,"goodsId=?",buyerId+"");
    }
    protected void deleteSuppiler(int supplierId){
        DataSupport.delete(Supplier.class,supplierId);
        DataSupport.deleteAll(Checkin.class,"goodsId=?",supplierId+"");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.blue_tooth_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



}
