package com.baosight.brightfish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.baosight.brightfish.model.HomeItem;
import com.baosight.brightfish.ui.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static final int CHECKIN_ITEM = 1;
    public static final int SUPPILER_ITEM = 2;
    public static final int CHECKOUT_ITEM = 3;
    public static final int BUYER_ITEM = 4;
    public static final int GOOD_ITEM = 5;
    public static final int DIANHUO_ITEM = 6;
    public static final int SEARCH_ITEM = 7;
    public static final int ANALYZE_ITEM = 8;
    public static final int ACCOUNT_ITEM = 9;
    public static final int SETTING_ITEM = 10;

    private List<HomeItem> homeItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        homeItems = new ArrayList<>();
        init();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        HomeAdapter adapter = new HomeAdapter(homeItems);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_rec);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    private void init() {
        HomeItem homeItem = new HomeItem(1, R.drawable.ic_checkin_36dp, "入库");
        homeItem.setColor("#65cb6b");
        homeItems.add(homeItem);
        HomeItem homeItem2 = new HomeItem(2, R.drawable.ic_supplier_36dp, "供应商");
        homeItem2.setColor("#65cb6b");

        homeItems.add(homeItem2);
        HomeItem homeItem3 = new HomeItem(3, R.drawable.ic_checkout_36dp, "出库");
        homeItem3.setColor("#3995df");

        homeItems.add(homeItem3);
        HomeItem homeItem4 = new HomeItem(4, R.drawable.ic_buyer_36dp, "买家");
        homeItem4.setColor("#3995df");

        homeItems.add(homeItem4);
        HomeItem homeItem5 = new HomeItem(5, R.drawable.ic_devices_other_orange_36dp, "货品");
        homeItem5.setColor("#fe6935");
        homeItems.add(homeItem5);
        HomeItem homeItem6 = new HomeItem(6, R.drawable.ic_checklist_36dp, "盘货");
        homeItem6.setColor("#fe6935");
        homeItems.add(homeItem6);
        HomeItem homeItem7 = new HomeItem(7, R.drawable.ic_search_36dp, "查找");
        homeItem7.setColor("#a078d7");
        homeItems.add(homeItem7);
        HomeItem homeItem8 = new HomeItem(8, R.drawable.ic_data_36dp, "数据分析");
        homeItem8.setColor("#a078d7");
        homeItems.add(homeItem8);
        HomeItem homeItem9 = new HomeItem(9, R.drawable.ic_account_36dp, "我的账号");
        homeItems.add(homeItem9);
        HomeItem homeItem10 = new HomeItem(10, R.drawable.ic_settings_black_36dp, "设置");
        homeItems.add(homeItem10);
    }
}
