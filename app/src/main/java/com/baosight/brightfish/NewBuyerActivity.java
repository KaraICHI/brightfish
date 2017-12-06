package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.baosight.brightfish.model.Buyer;

public class NewBuyerActivity extends EditActivity implements View.OnClickListener {

    public static void startBuyerActivity(Context context) {
        Intent intent = new Intent(context, NewBuyerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_new);
        initToolbar(R.color.colorBlue);
        initControls();

    }
    

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                showCameraDialog();
                break;
            case R.id.select_ablum_btn:
                BuyerAblumActivity.startBuyerAblumActivity(this);
                break;
            case R.id.commit_edit:
                saveBuyer();
                clearEditText();

        }
    }
    private void saveBuyer(){
        if (TextUtils.isEmpty(sku.getText())||TextUtils.isEmpty(name.getText())) {
            setAlertDialog();
        }else {
            Buyer supplier=new Buyer();
            supplier.setName(name.getText().toString());
            supplier.setSku(sku.getText().toString());
            supplier.setCellphoto(cellphone.getText().toString());
            supplier.setTelephone(telephone.getText().toString());
            supplier.setDescr(descr.getText().toString());
            supplier.setAddress(address.getText().toString());
            supplier.setEmail(email.getText().toString());
            supplier.setQq(qq.getText().toString());
            supplier.setWechat(wechat.getText().toString());
            supplier.setWebsite(website.getText().toString());
            supplier.save();
            Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
        }


    }




}
