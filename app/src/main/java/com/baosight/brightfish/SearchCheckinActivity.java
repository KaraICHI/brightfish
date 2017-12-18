package com.baosight.brightfish;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baosight.brightfish.model.Checkin;
import com.baosight.brightfish.model.Goods;
import com.baosight.brightfish.model.Supplier;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 查找入库
 */
public class SearchCheckinActivity extends SearchBasicActivity implements View.OnClickListener {
    private String mGoodsSkuStr,mSupplierSkuStr,mDateStr,mDescr,mDateMin,mDateMax,mAmount,mAmountMin,mAmountMax;

    public static void startSearchCheckinActivity(Context context) {
        Intent intent = new Intent(context, SearchCheckinActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_checkin);
        initControls(R.layout.item_add_condition_in);
        initToolbar(R.color.colorGreen);

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
                dialog.setContentView(R.layout.search_checkin_choose_attr);
                initAddConditionsDialog();
                break;
            //选择属性 描述
            case R.id.choose_attr_describe:
                dialog.setContentView(R.layout.dialog_search_attr_describe_in);
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
                dialog.setContentView(R.layout.dialog_search_attr_date_in);
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
                dialog.setContentView(R.layout.dialog_search_attr_price_in);
                initPriceAttrDialog();
                break;
            //选择属性 数量
            case R.id.choose_attr_amount:
                dialog.setContentView(R.layout.dialog_search_attr_amount_in);
                initAmountAttrDialog();
                break;
            //选择属性 供应商
            case R.id.choose_attr_supplier:
                dialog.setContentView(R.layout.dialog_search_attr_supplier_in);
                initSupplierDialog();
                break;
            //选择属性 货物
            case R.id.choose_attr_goods:
                dialog.setContentView(R.layout.dialog_search_attr_goods_in);
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
                amountOnly.setBackground(getDrawable(R.drawable.amount_activity_green));
                amountScope.setBackground(getDrawable(R.drawable.amount_normal));
                amountEquals.setVisibility(View.VISIBLE);
                amountMin.setVisibility(View.GONE);
                amountMax.setVisibility(View.GONE);
                break;
            //属性  范围
            case R.id.amount_scope:
                isOnly = false;
                amountOnly.setBackground(getDrawable(R.drawable.amount_normal));
                amountScope.setBackground(getDrawable(R.drawable.amount_activity_green));
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
            case R.id.check_good_menu:
                intent = new Intent(this, ChooseGoodsActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.check_supplier_menu:
                intent = new Intent(this, ChooseSupplierActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.search_btn:
                getSearchResult();
                SearchResultActivity.startSearchResultActivity(this,getSearchResult());
                break;
            default:
                break;

        }
    }

    private List<Checkin> getSearchResult() {
        List<Checkin> mCheckinList;
        Goods goods= DataSupport.where("sku=?",goodsSku.getText().toString()).find(Goods.class).get(0);
      /*  if(searchSupSku!=null){
            Supplier supplier=DataSupport.where("sku=?",searchSupSku.getText().toString()).find(Supplier.class).get(0);
        }*/
     /*   mGoodsSkuStr=goods.getSku();
        mSupplierSkuStr=supplier.getSku();
        mAmount=amountEquals.getText().toString();
        mAmountMin=amountMin.getText().toString();
        mAmountMax=amountMax.getText().toString();
        mDateStr=searchDateOnly.getText().toString();
        mDateMax=searchDateMax.getText().toString();
        mDateMin=searchDateMin.getText().toString();
        mDescr=searchDescribeWord.getText().toString();*/
        mCheckinList= DataSupport.where("goodsId=? ",goods.getId()+"").find(Checkin.class);

        return mCheckinList;
    }


}
