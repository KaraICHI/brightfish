package com.baosight.brightfish.ui.analyze;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.baosight.brightfish.R;
import com.baosight.brightfish.ui.BasicActivity;
import com.baosight.brightfish.ui.buyer.ChooseBuyerNoteActivity;
import com.baosight.brightfish.ui.search.choose.ChooseGoodsNoteActivity;
import com.baosight.brightfish.ui.search.choose.ChooseSupplierNoteActivity;

public class AnalyzeActivity extends BasicActivity implements View.OnClickListener{
    ImageButton toAll,toGoods,toSupplier,toBuyer,goodsMenu,supplierMenu,buyerMenu;
    private static final String TAG = "AnalyzeActivity";
    public static void startAnalyzeActivity(Context context) {
        Intent intent = new Intent(context, AnalyzeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);
        initControls();

    }

    private void initControls() {
        initToolbar(R.color.colorPur);
        toAll=(ImageButton) findViewById(R.id.to_all);
        toGoods=(ImageButton) findViewById(R.id.to_goods);
        toSupplier=(ImageButton) findViewById(R.id.to_supplier);
        toBuyer=(ImageButton) findViewById(R.id.to_buyer);
        buyerMenu=(ImageButton) findViewById(R.id.analyze_menu_buyer);
        supplierMenu=(ImageButton) findViewById(R.id.analyze_menu_supplier);
        goodsMenu=(ImageButton) findViewById(R.id.analyze_menu_goods);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_all:
                AnalyzeAllActivity.startAnalyzeAllActivity(this);
                Log.d(TAG, "onClick:-------------------all ");
                break;
            case R.id.to_buyer:
                Log.d(TAG, "onClick: -------------buy------------");
                break;
            case R.id.to_goods:
                GoodsAnalzyActivity.startGoodsAnalzyActivity(this);
                Log.d("qwqwqw", "onClick: ---------------g-==============");
                break;
            case R.id.analyze_menu_buyer:
                ChooseBuyerNoteActivity.startChooseBuyerActivity(this);
                break;
            case R.id.to_supplier:

                break;
            case R.id.analyze_menu_goods:
                ChooseGoodsNoteActivity.startChooseGoodsActivity(this);
                break;
            case R.id.analyze_menu_supplier:
                ChooseSupplierNoteActivity.startChooseSupplierNoteActivity(this);
                break;
            default:
                break;
        }

    }
}
