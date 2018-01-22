package com.baosight.brightfish.ui.analyze;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Buyer;
import com.baosight.brightfish.domain.Goods;
import com.baosight.brightfish.domain.Supplier;
import com.baosight.brightfish.ui.BasicActivity;
import com.baosight.brightfish.ui.buyer.ChooseBuyerActivity;
import com.baosight.brightfish.ui.search.choose.ChooseGoodsActivity;
import com.baosight.brightfish.ui.search.choose.ChooseSupplierActivity;
import com.baosight.brightfish.util.CurrentTimeUtil;

import java.util.List;

public class GoodsAnalzyActivity extends BasicActivity implements View.OnClickListener{
    private View point_layout;
    private RadioGroup sOb;
    private RadioGroup standard;
    private ImageButton supplier_list;
    private ImageButton buyer_list;
    LinearLayout defineTimeGroup;
    EditText startTime,endTime;
    private EditText device_SKU;
    private EditText device_name;
    private EditText point_SKU;
    private EditText point_name;


    public static void startGoodsAnalzyActivity(Context context) {
        Intent intent = new Intent(context, GoodsAnalzyActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_analzy);
        initToolbar(R.color.colorOrange);
        initControls();
    }

    @SuppressLint("WrongViewCast")
    private void initControls() {

        point_layout = findViewById(R.id.point_layout);
        defineTimeGroup=(LinearLayout) findViewById(R.id.define_time_group);
        standard = (RadioGroup) findViewById(R.id.time_standard);
        sOb = (RadioGroup) findViewById(R.id.supplier_or_buyer);
        supplier_list = (ImageButton) findViewById(R.id.select_supplier);
        buyer_list = (ImageButton) findViewById(R.id.select_buyer);
        startTime=(EditText) findViewById(R.id.start_time);
        endTime=(EditText) findViewById(R.id.end_time);
        device_SKU = (EditText)findViewById(R.id.supplier_SKU);
        device_name = (EditText)findViewById(R.id.supplier_name);
        point_SKU = (EditText)findViewById(R.id.point_SKU);
        point_name = (EditText)findViewById(R.id.point_name);
        startTime.setText( CurrentTimeUtil.getYMDTime(6));
        endTime.setText( CurrentTimeUtil.getYMDTime(0));
        standard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.standard:
                        defineTimeGroup.setVisibility(View.GONE);
                        break;
                    case R.id.define:
                        defineTimeGroup.setVisibility(View.VISIBLE);
                        Log.d("dddddd", "onCheckedChanged: ==============");
                        break;
                }
            }
        });
        sOb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.point_supplier:
                        supplier_list.setVisibility(View.VISIBLE);
                        buyer_list.setVisibility(View.GONE);
                        point_layout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.point_buyer:
                        supplier_list.setVisibility(View.GONE);
                        buyer_list.setVisibility(View.VISIBLE);
                        point_layout.setVisibility(View.VISIBLE);
                        break;

                }

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_supplier:
                Intent select_supplier = new Intent(GoodsAnalzyActivity.this, ChooseSupplierActivity.class);
                startActivityForResult(select_supplier,2);

                break;
            case R.id.select_buyer:
                Intent select_buyer = new Intent(GoodsAnalzyActivity.this, ChooseBuyerActivity.class);
                startActivityForResult(select_buyer,3);
                break;
            case R.id.device_list:
                Intent select_device = new Intent(GoodsAnalzyActivity.this, ChooseGoodsActivity.class);
                startActivityForResult(select_device,1);
                break;
            case R.id.start_time:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        startTime.setText(year+" "+(monthOfYear+1)+"月 "+dayOfMonth);
                    }
                },2017,5,1).show();
                break;
            case R.id.end_time:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        endTime.setText(year+" "+(monthOfYear+1)+"月 "+dayOfMonth);
                    }
                },2017,11,18).show();
                break;
            case R.id.activity:
                ActivtyAllActivity.startActivityAllActivity(this);
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                Goods goods= (Goods) data.getBundleExtra("bundle").getSerializable("goods");
                device_SKU.setText(goods.getSku());
                device_name.setText(goods.getName());
                break;
            case 2:
                Supplier supplier= (Supplier) data.getBundleExtra("bundle").getSerializable("supplier");
                Log.d("sssss", "onActivityResult: =================="+supplier.getName());
                point_SKU.setText(supplier.getSku());
                point_name.setText(supplier.getName());
                break;
            case 3:
                Buyer buyer= (Buyer) data.getBundleExtra("bundle").getSerializable("buyer");
                point_SKU.setText(buyer.getSku());
                point_name.setText(buyer.getName());
                break;
            default:break;
        }
    }
}
