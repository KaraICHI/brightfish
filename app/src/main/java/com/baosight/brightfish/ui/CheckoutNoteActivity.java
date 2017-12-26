package com.baosight.brightfish.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.baosight.brightfish.R;
import com.baosight.brightfish.model.Checkout;
import com.baosight.brightfish.model.Goods;
import com.baosight.brightfish.model.Buyer;
import com.baosight.brightfish.ui.buyer.BuyerActivity;

import org.litepal.crud.DataSupport;

public class CheckoutNoteActivity extends CheckBasicActivity {

    Checkout checkout;
    EditText buyerSku;
    EditText buyerName;
    ImageButton toGoods,tobuyer;
    Goods goods;
    Buyer buyer;
    public static void startCheckoutNoteActivity(Context context, Checkout checkout){
        Intent intent=new Intent(context,CheckoutNoteActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("checkout",checkout);
        intent.putExtra("bundle",bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_note);
        initControls();
        initToolbar(R.color.colorBlue);
        showcheckoutNote();
    }


    private void showcheckoutNote() {
        buyerName = (EditText) findViewById(R.id.buyer_name_checkin);
        buyerSku = (EditText) findViewById(R.id.buyer_sku_checkin);
        toGoods=(ImageButton) findViewById(R.id.to_goods);
        tobuyer=(ImageButton) findViewById(R.id.to_buyer);
        checkout=(Checkout) getIntent().getBundleExtra("bundle").getSerializable("checkout");
        goods= DataSupport.find(Goods.class,checkout.getGoodsId());
        buyer=DataSupport.find(Buyer.class,checkout.getBuyerId());
        goodsSku.setText(goods.getSku());
        goodsName.setText(goods.getName());
        price.setText(checkout.getPrice()+"");
        amount.setText(checkout.getAmount()+"");
        currentTime.setText(checkout.getCheckinDate());
        description.setText(checkout.getDescr());
        buyerName.setText(buyer.getName());
        buyerSku.setText(buyer.getSku());
        tobuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyerActivity.startBuyerActivity(CheckoutNoteActivity.this,buyer);
            }
        });
        toGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsActivity.startGoodsActivity(CheckoutNoteActivity.this,goods);
            }
        });
    }

}
