package com.baosight.brightfish;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class SearchCheckoutActivity extends SearchBasicActivity implements View.OnClickListener {

    public static void startSearchCheckoutActivity(Context context) {
        Intent intent = new Intent(context, SearchCheckoutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_checkout);
        initControls(R.layout.item_add_condition_out);
        initToolbar(R.color.colorBlue);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //显示 选择货品排序
            case R.id.search_sort_btn:
                showSortDialog();
                break;
            case R.id.search_goods_close_btn:
                View topSelect = findViewById(R.id.goods_select_top);
                assert topSelect != null;
                topSelect.setVisibility(View.GONE);
                break;
            case R.id.add_conditions_btn:
                //更多条件 对话框
                dialog = new Dialog(this, R.style.NoTitleDialog);
                dialog.setContentView(R.layout.search_checkout_choose_attr);
                initAddConditionsDialog();
                break;
            //选择属性 描述
            case R.id.choose_attr_describe:
                dialog.setContentView(R.layout.dialog_search_attr_describe_out);
                initDescribeDialog();
                break;
            case R.id.search_describe_attr_commit:
                String descr = searchDescribeWord.getText().toString();
                if (!TextUtils.isEmpty(descr)) {
                    addConditions("描述", descr);
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "描述不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            //选择属性 日期时间
            case R.id.choose_attr_date:
                dialog.setContentView(R.layout.dialog_search_attr_date_out);
                showDateTimeDialog();
                break;
            //选择日期
            case R.id.search_date_only:
                showDatePicker(searchDateOnly, year, month, day);
                break;
            case R.id.search_time_only:
                showTimePicker(searchTimeOnly, hour, day);
                break;
            case R.id.search_date_min:
                showDatePicker(searchDateMin, year, month, day);
                break;
            case R.id.search_date_max:
                showDatePicker(searchDateMax, year, month, day);
                break;
            case R.id.search_time_max:
                showTimePicker(searchTimeMax, hour, minute);
                break;
            case R.id.search_time_min:
                showTimePicker(searchTimeMin, hour, minute);
                break;
            //选择属性 价格
            case R.id.choose_attr_price:
                dialog.setContentView(R.layout.dialog_search_attr_price_out);
                initPriceAttrDialog();
                break;
            //选择属性 数量
            case R.id.choose_attr_amount:
                dialog.setContentView(R.layout.dialog_search_attr_amount_out);
                initAmountAttrDialog();
                break;
            //选择属性 供应商
            case R.id.choose_attr_supplier:
                dialog.setContentView(R.layout.dialog_search_attr_supplier_out);
                initSupplierDialog();
                break;
            //选择属性 货物
            case R.id.choose_attr_goods:
                dialog.setContentView(R.layout.dialog_search_attr_goods_out);
                initGoodsDialog();
                break;
            //指定货物 确定
            case R.id.search_goods_attr_commit:
                selectName = searchGoodsName.getText().toString();
                selectSku = searchGoodsSku.getText().toString();
                addConditions("货品", selectName);
                dialog.dismiss();
                break;
            //指定供应商 确定
            case R.id.search_supplier_attr_commit:
                selectName = searchSupName.getText().toString();
                selectSku = searchSupSku.getText().toString();
                addConditions("供应商", selectName);
                dialog.dismiss();
                break;

            case R.id.search_date_attr_commit:
                if (isOnly) {
                    addConditions("日期-时间", searchDateOnly.getText().toString() + " " + searchTimeOnly.getText().toString());
                } else {
                    addConditions("日期-时间", searchDateMin.getText().toString() + " " + searchTimeMin.getText().toString()
                            + " ~ " + searchDateMax.getText().toString() + " " + searchTimeMax.getText().toString());
                }
                dialog.dismiss();
                break;


            //取消 dialog
            case R.id.search_goods_attr_cancel:
            case R.id.choose_attr_cancel:
                dialog.dismiss();
                break;

            //属性  指定
            case R.id.amount_only:
                isOnly = true;
                amountOnly.setBackground(getDrawable(R.drawable.amount_activity_blue));
                amountScope.setBackground(getDrawable(R.drawable.amount_normal));
                amountEquals.setVisibility(View.VISIBLE);
                amountMin.setVisibility(View.GONE);
                amountMax.setVisibility(View.GONE);
                break;
            //属性  范围
            case R.id.amount_scope:
                isOnly = false;
                amountOnly.setBackground(getDrawable(R.drawable.amount_normal));
                amountScope.setBackground(getDrawable(R.drawable.amount_activity_blue));
                amountEquals.setVisibility(View.GONE);
                amountMin.setVisibility(View.VISIBLE);
                amountMax.setVisibility(View.VISIBLE);
                break;
            //指定 数量
            case R.id.search_amount_attr_commit:
                if (isOnly) {
                    addConditions("数量", searchAmountOnly.getText().toString());
                } else {
                    addConditions("数量", searchAmountMin.getText().toString() + " ~ "
                            + searchAmountMax.getText().toString());

                }
                dialog.dismiss();
                break;
            //指定 价格
            case R.id.search_price_attr_commit:
                if (isOnly) {
                    addConditions("价格", searchPriceOnly.getText().toString());
                } else {
                    addConditions("价格", searchPriceMin.getText().toString() + " ~ "
                            + searchPriceMax.getText().toString());
                }
                dialog.dismiss();
                break;

            default:
                break;

        }
    }
}
