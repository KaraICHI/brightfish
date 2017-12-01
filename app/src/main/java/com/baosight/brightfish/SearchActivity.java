package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageButton chooseCheckin, chooseCheckout, chooseGoods, chooseSupplier, chooseBuyer, chooseCheckList,
            searchCheckin, searchCheckout, searchGoods, searchSupplier, searchBuyer, searchCheckList;
    private static final String TAG = "SearchActivity";


    public static void startSearchActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPur));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initControls();

    }

    private void initControls() {
        chooseBuyer = (ImageButton) findViewById(R.id.choose_buyer);
        chooseCheckin = (ImageButton) findViewById(R.id.choose_checkin);
        chooseCheckout = (ImageButton) findViewById(R.id.choose_checkout);
        chooseCheckList = (ImageButton) findViewById(R.id.choose_dianhuo);
        chooseGoods = (ImageButton) findViewById(R.id.choose_goods);
        chooseSupplier = (ImageButton) findViewById(R.id.choose_supplier);
        searchBuyer = (ImageButton) findViewById(R.id.search_buyer);
        searchCheckin = (ImageButton) findViewById(R.id.search_checkin);
        searchCheckout = (ImageButton) findViewById(R.id.search_checkout);
        searchGoods = (ImageButton) findViewById(R.id.search_goods);
        searchCheckList = (ImageButton) findViewById(R.id.search_dianhuo);
        searchSupplier = (ImageButton) findViewById(R.id.search_supplier);
        chooseBuyer.setOnClickListener(this);
        chooseCheckList.setOnClickListener(this);
        chooseSupplier.setOnClickListener(this);
        chooseGoods.setOnClickListener(this);
        chooseCheckout.setOnClickListener(this);
        chooseCheckin.setOnClickListener(this);
        searchBuyer.setOnClickListener(this);
        searchCheckList.setOnClickListener(this);
        searchCheckin.setOnClickListener(this);
        searchCheckout.setOnClickListener(this);
        searchGoods.setOnClickListener(this);
        searchSupplier.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.blue_tooth_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_buyer:
                ChooseBuyerActivity.startChooseBuyerActivity(this);
                break;
            case R.id.choose_checkin:
                ChooseCheckinActivity.startChooseCheckinActivity(this);
                break;
            case R.id.choose_checkout:
                ChooseCheckoutActivity.startChooseCheckoutActivity(this);
                break;
            case R.id.choose_supplier:
                ChooseSupplierActivity.startChooseSupplierActivity(this);
                break;
            case R.id.choose_dianhuo:
                ChooseChecklistActivity.startChooseChecklistActivity(this);
                break;
            case R.id.choose_goods:
                ChooseGoodsActivity.startChooseGoodsActivity(this);
                break;
            case R.id.search_buyer:
                break;
            case R.id.search_checkin:
                SearchCheckinActivity.startSearchCheckinActivity(this);
                break;
            case R.id.search_checkout:
                SearchCheckoutActivity.startSearchCheckoutActivity(this);
                break;
            case R.id.search_dianhuo:
                break;
            case R.id.search_goods:
                break;
            case R.id.search_supplier:
                break;
            default:

        }
    }
}
