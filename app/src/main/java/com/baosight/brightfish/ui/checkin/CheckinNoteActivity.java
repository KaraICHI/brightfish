package com.baosight.brightfish.ui.checkin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Checkin;
import com.baosight.brightfish.domain.Goods;
import com.baosight.brightfish.domain.Supplier;
import com.baosight.brightfish.ui.CheckBasicActivity;
import com.baosight.brightfish.ui.goods.GoodsActivity;
import com.baosight.brightfish.ui.supplier.SupplierActivity;

import org.litepal.crud.DataSupport;

public class CheckinNoteActivity extends CheckBasicActivity {
    Checkin checkin;
    EditText supplierSku;
    EditText supplierName;
    ImageButton toGoods,toSupplier;
    Goods goods;
    Supplier supplier;

    public static void startCheckinNoteActivity(Context context, Checkin checkin){
        Intent intent=new Intent(context,CheckinNoteActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("checkin",checkin);
        intent.putExtra("bundle",bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_note);
        initControls();
        initToolbar(R.color.colorGreen);
        showCheckinNote();
    }

    private void showCheckinNote() {
        supplierName = (EditText) findViewById(R.id.supplier_name_checkin);
        supplierSku = (EditText) findViewById(R.id.supplier_sku_checkin);
        toGoods=(ImageButton) findViewById(R.id.to_goods);
        toSupplier=(ImageButton) findViewById(R.id.to_supplier);
        checkin=(Checkin) getIntent().getBundleExtra("bundle").getSerializable("checkin");
        goods= DataSupport.find(Goods.class,checkin.getGoodsId());
        supplier=DataSupport.find(Supplier.class,checkin.getSupplierId());
        goodsSku.setText(goods.getSku());
        goodsName.setText(goods.getName());
        price.setText(checkin.getPrice()+"");
        amount.setText(checkin.getAmount()+"");
        currentTime.setText(checkin.getCheckinDate());
        description.setText(checkin.getDescr());
        supplierName.setText(supplier.getName());
        supplierSku.setText(supplier.getSku());
        toSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupplierActivity.startSupplierActivity(CheckinNoteActivity.this,supplier);
            }
        });
        toGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsActivity.startGoodsActivity(CheckinNoteActivity.this,goods);
            }
        });
    }


}
