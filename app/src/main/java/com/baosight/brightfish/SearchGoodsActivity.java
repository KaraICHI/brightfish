package com.baosight.brightfish;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SearchGoodsActivity extends SearchBasicActivity {


    private static final int SKU_DIALOG = 0;
    private static final int NAME_DIALOG = 1;
    private static final int SPEC_DIALOG = 3;
    private static final int CATA_DIALOG = 2;
    private static final int COLOR_DIALOG = 4;
    private static final int SIZE_DIALOG = 5;
    private static final int DESC_DIALOG =6;
    private static final int STORE_DIALOG = 7;
    private static final int PRICE_IN_DIALOG =8 ;
    private static final int PRICE_OUT_DIALOG =9;
    private TextView attrTitle;
    private EditText attrEquals;
    private Button dialogCommit;
    private int currentDialog;


    public static void startSearchGoodsActivity(Context context) {
        Intent intent = new Intent(context, SearchGoodsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_goods);
        initAdapter(R.layout.item_add_condition_goods);
        initControls();
        initToolbar(R.color.colorOrange);
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
                initAddConditionsDialog();
                break;
            case R.id.choose_attr_sku:
                showOneLineDialog("SKU");
                currentDialog=SKU_DIALOG;
                break;
            case R.id.choose_attr_name:
                showOneLineDialog("名称");
                currentDialog=NAME_DIALOG;
                break;
            case R.id.choose_attr_catagory:
                showTwoLineDialog("类别");
                currentDialog=CATA_DIALOG;
                break;
            case R.id.choose_attr_spec:
                showOneLineDialog("规格");
                currentDialog=SPEC_DIALOG;
                break;
            case R.id.choose_attr_color:
                showOneLineDialog("颜色");
                currentDialog=COLOR_DIALOG;
                break;
            case R.id.choose_attr_size:
                showOneLineDialog("尺寸");
                currentDialog=SIZE_DIALOG;
                break;
            case R.id.choose_attr_describe:
                showOneLineDialog("描述");
                currentDialog=DESC_DIALOG;
                break;
            case R.id.choose_attr_store:
                showTwoLineDialog("现有库存数量");
                currentDialog=STORE_DIALOG;
                break;

            case R.id.choose_attr_price_in:
                showTwoLineDialog("默认入库价格");
                currentDialog=PRICE_IN_DIALOG;
                break;
            case R.id.choose_attr_price_out:
                showTwoLineDialog("默认出库价格");
                currentDialog=PRICE_OUT_DIALOG;
                break;
            case R.id.search_goods_attr_commit:
                commitAttr();

            default:
                break;


        }
    }

    protected void commitAttr(){
        switch (currentDialog){
            case SKU_DIALOG:case NAME_DIALOG:case SPEC_DIALOG:case SIZE_DIALOG:case COLOR_DIALOG:case DESC_DIALOG:
                addConditions(attrTitle.getText().toString(),attrEquals.getText().toString());
        }
    }
    protected void showOneLineDialog(String title){
        dialog.setContentView(R.layout.dialog_search_attr_oneline);
        attrTitle=(TextView) dialog.findViewById(R.id.search_goods_attr_title);
        attrTitle.setText(title);
        attrEquals=(EditText)dialog.findViewById(R.id.search_goods_attr_equals);
        dialogCommit=(Button) dialog.findViewById(R.id.search_goods_attr_commit);
        dialogCommit.setOnClickListener(this);
    }
    protected void showTwoLineDialog(String title){
        dialog.setContentView(R.layout.dialog_search_attr_twoline);
        attrTitle=(TextView) dialog.findViewById(R.id.search_goods_attr_title);
        attrTitle.setText(title);
        initChooseScope();
    }

    protected void  initAddConditionsDialog(){
        dialog = new Dialog(this, R.style.NoTitleDialog);
        dialog.setContentView(R.layout.search_goods_choose_attr);
        dialog.show();
        TextView skuAttr=(TextView) findViewById(R.id.choose_attr_sku);
        TextView nameAttr=(TextView) findViewById(R.id.choose_attr_name);
        TextView catagoryAttr=(TextView) findViewById(R.id.choose_attr_catagory);
        TextView specAttr=(TextView)findViewById(R.id.choose_attr_spec);
        TextView colorAttr=(TextView) findViewById(R.id.choose_attr_color);
        TextView storeAttr=(TextView) findViewById(R.id.choose_attr_store);
        TextView descAttr=(TextView) findViewById(R.id.choose_attr_describe);
        TextView priceInAttr=(TextView) findViewById(R.id.choose_attr_price_in);
        TextView priceOutAttr=(TextView) findViewById(R.id.choose_attr_price_out);
        assert skuAttr != null;
        skuAttr.setOnClickListener(this);
        assert nameAttr != null;
        nameAttr.setOnClickListener(this);
        assert catagoryAttr != null;
        catagoryAttr.setOnClickListener(this);
        assert specAttr != null;
        specAttr.setOnClickListener(this);
        assert colorAttr != null;
        colorAttr.setOnClickListener(this);
        assert storeAttr != null;
        storeAttr.setOnClickListener(this);
        assert descAttr != null;
        descAttr.setOnClickListener(this);
        assert priceInAttr != null;
        priceInAttr.setOnClickListener(this);
        assert priceOutAttr != null;
        priceOutAttr.setOnClickListener(this);
    }
}
