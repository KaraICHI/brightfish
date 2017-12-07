package com.baosight.brightfish;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.baosight.brightfish.model.Buyer;
import com.baosight.brightfish.model.Checkout;
import com.baosight.brightfish.model.Goods;
import com.baosight.brightfish.ui.ChooseBuyerDialogAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

public class CheckoutActivity  extends CheckBasicActivity implements View.OnClickListener{
    @SuppressLint("StaticFieldLeak")
    static EditText buyerSku;
    @SuppressLint("StaticFieldLeak")
    static EditText buyerName;
    ImageButton buyerMenu, buyerRefesh;
    public static Goods goods;
    public static Buyer buyer;

    public static void startCheckoutActivity(Context context) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        initToolbar(R.color.colorBlue);
        initControls();
        initBuyerPart();

    }
    protected void initBuyerPart(){
        buyerName = (EditText) findViewById(R.id.buyer_name_checkin);
        buyerSku = (EditText) findViewById(R.id.buyer_sku_checkin);
        buyerMenu = (ImageButton) findViewById(R.id.check_buyer_menu);
        buyerRefesh = (ImageButton) findViewById(R.id.check_good_refesh);
        buyerMenu.setOnClickListener(this);
        buyerRefesh.setOnClickListener(this);
        editTexts.add(buyerSku);
        editTexts.add(buyerName);
        setCheckLisener();
    }



    @Override
    public void onClick(View v) {
        if (v.getClass() == android.support.v7.widget.AppCompatEditText.class) {
            content += goodsSku.getText();
            isFocus = true;
            invalidateOptionsMenu();
        }
        switch (v.getId()) {
            case R.id.check_good_menu:
                Intent intent = new Intent(this, ChooseGoodsActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.check_good_refesh:
                goodsSku.setText("");
                goodsName.setText("");
                break;
            case R.id.check_buyer_menu:
                Intent intent2 = new Intent(this, ChooseBuyerActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.check_buyer_refesh:
                buyerSku.setText("");
                buyerName.setText("");
                break;
            case R.id.photo_goods:
                CheckinAblumActivity.startCheckinAblumActivity(CheckoutActivity.this);
                break;
            case R.id.checkin_commit:
                if((!isEditTextBlank()&&isBuyerExist())){
                    saveCheckout();
                    clearEditText();
                    buyerSku.setText("");
                    buyerName.setText("");
                    Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_new_buyer:
                NewBuyerActivity.startBuyerActivity(CheckoutActivity.this);
                break;
            case R.id.add_new_goods:
                NewGoodsActivity.startGoodsActivity(CheckoutActivity.this);
                break;
            case R.id.add_cancel:
                dialog.dismiss();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check_mark:
                saveCheckout();
                break;
            case R.id.blueTooth:
                Toast.makeText(CheckoutActivity.this, "开启蓝牙扫码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.default_param:
                Toast.makeText(CheckoutActivity.this, "货品默认参数", Toast.LENGTH_SHORT).show();
                break;
            case R.id.kuaiSao:
                Toast.makeText(CheckoutActivity.this, "开启快扫模式", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveCheckout() {
        if((!isEditTextBlank()&&isBuyerExist())){
            Checkout checkout=new Checkout();
            checkout.setGoods(goods);
            checkout.setAmount(Integer.parseInt(amount.getText().toString()));
            checkout.setPrice(Long.parseLong(price.getText().toString()));
            checkout.setDescr(description.getText().toString());
            checkout.setBuyer(buyer);
            checkout.save();
            clearEditText();
            buyerSku.setText("");
            buyerName.setText("");
            Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isBuyerExist() {
        if (!selectBuyer(buyerSku.getText().toString())) {
            showNoThatBuyerDialog();
            return false;
        }
        return true;

    }

    private boolean selectBuyer(String s) {
        List<Buyer> buyerList= DataSupport.where("sku=?",s).find(Buyer.class);
        if(buyerList.size()>=1){
            buyer=buyerList.get(0);
            return true;
        }
        return false;

    }

    private void showNoThatBuyerDialog() {
        dialog = new Dialog(CheckoutActivity.this);
        dialog.setTitle("没有该买家！");
        dialog.setContentView(R.layout.dialog_no_buyer);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.dialog_rec);
        Button addNew=(Button) dialog.findViewById(R.id.add_new_buyer);
        Button addCancel=(Button) dialog.findViewById(R.id.add_cancel);
        addCancel.setOnClickListener(this);
        addNew.setOnClickListener(this);
        ChooseBuyerDialogAdapter adapter = new ChooseBuyerDialogAdapter(DataSupport.findAll(Buyer.class),dialog);
        LinearLayoutManager layoutManager=new LinearLayoutManager(CheckoutActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        dialog.show();
    }
    public static void refesh(){
        if (goods != null) {
            goodsSku.setText(goods.getSku());
            goodsName.setText(goods.getName());
        }
        if (buyer != null) {
            buyerSku.setText(buyer.getSku());
            buyerName.setText(buyer.getName());
        }


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
                    buyer = (Buyer) data.getBundleExtra("bundle").getSerializable("buyer");
                }
        }
        refesh();

    }

}
