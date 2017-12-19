package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class SearchActivity extends BasicActivity implements View.OnClickListener {
    ImageButton chooseCheckin, chooseCheckout, chooseGoods, chooseSupplier, chooseBuyer, chooseCheckList,
            searchCheckin, searchCheckout, searchGoods, searchSupplier, searchBuyer, searchCheckList;


    public static void startSearchActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
       initToolbar(R.color.colorPur);
        initControls();

    }

    private void initControls() {
        chooseBuyer = (ImageButton) findViewById(R.id.choose_buyer);
        chooseCheckin = (ImageButton) findViewById(R.id.choose_checkin);
        chooseCheckout = (ImageButton) findViewById(R.id.choose_checkout);
        chooseCheckList = (ImageButton) findViewById(R.id.choose_checklist);
        chooseGoods = (ImageButton) findViewById(R.id.choose_goods);
        chooseSupplier = (ImageButton) findViewById(R.id.choose_supplier);
        searchBuyer = (ImageButton) findViewById(R.id.search_buyer);
        searchCheckin = (ImageButton) findViewById(R.id.search_checkin);
        searchCheckout = (ImageButton) findViewById(R.id.search_checkout);
        searchGoods = (ImageButton) findViewById(R.id.search_goods);
        searchCheckList = (ImageButton) findViewById(R.id.search_checklist);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_buyer:
                ChooseBuyerNoteActivity.startChooseBuyerActivity(this);
                break;
            case R.id.choose_checkin:
                ChooseCheckinActivity.startChooseCheckinActivity(this);
                break;
            case R.id.choose_checkout:
                ChooseCheckoutActivity.startChooseCheckoutActivity(this);
                break;
            case R.id.choose_supplier:
                ChooseSupplierNoteActivity.startChooseSupplierNoteActivity(this);
                break;

            case R.id.choose_goods:
                ChooseGoodsNoteActivity.startChooseGoodsActivity(this);
                break;
            case R.id.choose_checklist:
                ChooseChecklistNoteActivity.startChecklistNoteHistoryActivity(this);
                break;
            case R.id.search_buyer:
                break;
            case R.id.search_checkin:
                SearchCheckinActivity.startSearchCheckinActivity(this);
                break;
            case R.id.search_checkout:
                SearchCheckoutActivity.startSearchCheckoutActivity(this);
                break;
            case R.id.search_checklist:
                break;
            case R.id.search_goods:
                break;
            case R.id.search_supplier:
                break;
            default:

        }
    }
}
