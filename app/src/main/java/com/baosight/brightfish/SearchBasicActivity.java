package com.baosight.brightfish;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baosight.brightfish.model.ConditionItem;
import com.baosight.brightfish.model.Goods;
import com.baosight.brightfish.model.Supplier;
import com.baosight.brightfish.ui.ConditionAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchBasicActivity extends BasicActivity implements View.OnClickListener {
    Toolbar toolbar;
    Boolean isOnly = true;
    List<ConditionItem> conditionItemList = new ArrayList<>();
    RecyclerView recyclerView;
    ConditionAdapter adapter;
    ImageView addConditions, searchSort, goodsCancel,goodsMenu;
    String selectName, selectSku;
    static EditText goodsName,goodsSku;
    static EditText searchGoodsName, searchGoodsSku, searchSupName, searchSupSku, searchAmountOnly, searchAmountMin, searchAmountMax, searchPriceOnly, searchPriceMin, searchPriceMax, searchDescribeWord, searchDateOnly,
            searchTimeOnly, searchDateMin, searchDateMax, searchTimeMin, searchTimeMax;
    Button amountOnly, amountScope,searchBtn;
    View amountEquals, amountMin, amountMax;
    Dialog dialog;
    int year, month, day, hour, minute;
    static Goods goods;
    static Supplier supplier;
    Intent intent;

    /**
     * 初始化控件
     */

    protected void initControls(int itemLayout) {

        addConditions = (ImageView) findViewById(R.id.add_conditions_btn);
        searchSort = (ImageView) findViewById(R.id.search_sort_btn);
        addConditions.setOnClickListener(this);
        searchSort.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ConditionAdapter(conditionItemList);
        adapter.setItemLayout(itemLayout);
        recyclerView = (RecyclerView) findViewById(R.id.search_conditions_rec);
        assert recyclerView != null;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        goodsCancel = (ImageView) findViewById(R.id.search_goods_close_btn);
        assert goodsCancel != null;
        goodsCancel.setOnClickListener(this);
        goodsMenu=(ImageView) findViewById(R.id.check_good_menu);
        goodsMenu.setOnClickListener(this);
        goodsName = (EditText) findViewById(R.id.select_goods_name);
        goodsSku = (EditText) findViewById(R.id.select_goods_sku);
        searchBtn=(Button) findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);

    }



    protected void initDescribeDialog() {
        searchDescribeWord = (EditText) dialog.findViewById(R.id.search_describe_word);
        Button selectDescrCommit = (Button) dialog.findViewById(R.id.search_describe_attr_commit);
        Button selectDexcrCancel = (Button) dialog.findViewById(R.id.search_goods_attr_cancel);
        selectDescrCommit.setOnClickListener(this);
        selectDexcrCancel.setOnClickListener(this);
    }


    protected void addConditions(String conditionName, String conditionContent) {
        ConditionItem conditionItem = new ConditionItem(conditionName, conditionContent);
        conditionItemList.add(conditionItem);
        adapter.notifyItemChanged(conditionItemList.size() - 1);
        recyclerView.scrollToPosition(conditionItemList.size() - 1);
    }

    protected void initAddConditionsDialog() {
        dialog.setCanceledOnTouchOutside(true);
        TextView goodsAttr = (TextView) dialog.findViewById(R.id.choose_attr_goods);
        TextView supplierAttr = (TextView) dialog.findViewById(R.id.choose_attr_supplier);
        TextView amountAttr = (TextView) dialog.findViewById(R.id.choose_attr_amount);
        TextView priceAttr = (TextView) dialog.findViewById(R.id.choose_attr_price);
        TextView descrAttr = (TextView) dialog.findViewById(R.id.choose_attr_describe);
        TextView dateAttr = (TextView) dialog.findViewById(R.id.choose_attr_date);
        Button chooseAttrCancel = (Button) dialog.findViewById(R.id.choose_attr_cancel);
        chooseAttrCancel.setOnClickListener(this);
        goodsAttr.setOnClickListener(this);
        supplierAttr.setOnClickListener(this);
        amountAttr.setOnClickListener(this);
        priceAttr.setOnClickListener(this);
        descrAttr.setOnClickListener(this);
        dateAttr.setOnClickListener(this);
        dialog.show();
    }

    protected void initChooseScope() {
        isOnly = true;
        amountOnly = (Button) dialog.findViewById(R.id.amount_only);
        amountScope = (Button) dialog.findViewById(R.id.amount_scope);
        amountEquals = dialog.findViewById(R.id.amount_equals);
        amountMin = dialog.findViewById(R.id.amount_min);
        amountMax = dialog.findViewById(R.id.amount_max);
        amountOnly.setOnClickListener(this);
        amountScope.setOnClickListener(this);
    }

    protected void initPriceAttrDialog() {
        initChooseScope();
        Button selectPriceCommit = (Button) dialog.findViewById(R.id.search_price_attr_commit);
        Button selectPriceCancel = (Button) dialog.findViewById(R.id.search_goods_attr_cancel);
        searchPriceOnly = (EditText) dialog.findViewById(R.id.search_price_only);
        searchPriceMax = (EditText) dialog.findViewById(R.id.search_price_max);
        searchPriceMin = (EditText) dialog.findViewById(R.id.search_price_min);
        selectPriceCommit.setOnClickListener(this);
        selectPriceCancel.setOnClickListener(this);
    }

    protected void initAmountAttrDialog() {

        initChooseScope();
        Button selectAmountCommit = (Button) dialog.findViewById(R.id.search_amount_attr_commit);
        Button selectAmountCancel = (Button) dialog.findViewById(R.id.search_goods_attr_cancel);
        searchAmountOnly = (EditText) dialog.findViewById(R.id.search_amount_only);
        searchAmountMax = (EditText) dialog.findViewById(R.id.search_amount_max);
        searchAmountMin = (EditText) dialog.findViewById(R.id.search_amount_min);
        selectAmountCommit.setOnClickListener(this);
        selectAmountCancel.setOnClickListener(this);
    }

    protected void initSupplierDialog() {
        Button selectSupplierCommit = (Button) dialog.findViewById(R.id.search_supplier_attr_commit);
        Button selectSupplierCancel = (Button) dialog.findViewById(R.id.search_supplier_attr_cancel);
        ImageView supplierMenu=(ImageView) dialog.findViewById(R.id.check_supplier_menu);
        searchSupName = (EditText) dialog.findViewById(R.id.search_supplier_name);
        searchSupSku = (EditText) dialog.findViewById(R.id.search_supplier_sku);
        supplierMenu.setOnClickListener(this);
        selectSupplierCancel.setOnClickListener(this);
        selectSupplierCommit.setOnClickListener(this);
    }

    protected void initGoodsDialog() {
        Button selectGoodsCommit = (Button) dialog.findViewById(R.id.search_goods_attr_commit);
        Button selectGoodsCancel = (Button) dialog.findViewById(R.id.search_goods_attr_cancel);
        searchGoodsName = (EditText) dialog.findViewById(R.id.search_goods_name);
        searchGoodsSku = (EditText) dialog.findViewById(R.id.search_goods_sku);
        selectGoodsCancel.setOnClickListener(this);
        selectGoodsCommit.setOnClickListener(this);
    }

    /**
     * 显示日期时间
     */
    @SuppressLint("SetTextI18n")
    protected void showDateTimeDialog() {
        initChooseScope();
        //获取Calendar对象，用于获取当前时间
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        searchDateOnly = (EditText) dialog.findViewById(R.id.search_date_only);
        searchTimeOnly = (EditText) dialog.findViewById(R.id.search_time_only);
        searchDateMin = (EditText) dialog.findViewById(R.id.search_date_min);
        searchDateMax = (EditText) dialog.findViewById(R.id.search_date_max);
        searchTimeMin = (EditText) dialog.findViewById(R.id.search_time_min);
        searchTimeMax = (EditText) dialog.findViewById(R.id.search_time_max);
        Button selectDateCommit = (Button) dialog.findViewById(R.id.search_date_attr_commit);
        Button selectDateCancel = (Button) dialog.findViewById(R.id.search_goods_attr_cancel);
        searchDateOnly.setText(year + " " + month + "月" + day + "日");
        searchTimeOnly.setText(hour + ":" + minute);
        searchDateMin.setText(year + " " + (month - 1) + "月" + day + "日");
        searchDateMax.setText(year + " " + month + "月" + day + "日");
        searchTimeMin.setText("00:00");
        searchTimeMax.setText(hour + ":" + minute);
        selectDateCommit.setOnClickListener(this);
        selectDateCancel.setOnClickListener(this);
        searchDateOnly.setOnClickListener(this);
        searchTimeOnly.setOnClickListener(this);
        searchDateMax.setOnClickListener(this);
        searchDateMin.setOnClickListener(this);
        searchTimeMax.setOnClickListener(this);
        searchTimeMin.setOnClickListener(this);


    }

    protected void showDatePicker(final TextView date, int year, int month, int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(SearchBasicActivity.this, new DatePickerDialog.OnDateSetListener() {
            //选择完日期后会调用该回调函数
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //因为monthOfYear会比实际月份少一月所以这边要加1
                date.setText(year + " " + (monthOfYear + 1) + "月" + dayOfMonth );
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    protected void showTimePicker(final TextView time, int hour, int minute) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(SearchBasicActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.setText(hourOfDay + ":" + minute);
            }
        }, hour, minute, true);
        timePickerDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    goods = (Goods) data.getBundleExtra("bundle").getSerializable("goods");
                }
            case 2:
                if (resultCode == RESULT_OK) {
                    supplier = (Supplier) data.getBundleExtra("bundle").getSerializable("supplier");
                }

        }
        refesh();

    }
    public static void refesh(){
        if (goods != null&&goodsSku!=null) {
            goodsSku.setText(goods.getSku());
            goodsName.setText(goods.getName());
        }if(supplier!=null&&searchSupSku!=null){
            searchSupName.setText(supplier.getName());
            searchSupSku.setText(supplier.getSku());
        }



    }

    @Override
    public void onClick(View v) {

    }
}
