package com.baosight.brightfish.ui.buyer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Buyer;
import com.baosight.brightfish.ui.EditActivity;
import com.baosight.brightfish.ui.album.BuyerAblumActivity;

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
            Buyer buyer=new Buyer();
            buyer.setName(name.getText().toString());
            buyer.setSku(sku.getText().toString());
            buyer.setCellphone(cellphone.getText().toString());
            buyer.setTelephone(telephone.getText().toString());
            buyer.setDescr(descr.getText().toString());
            buyer.setAddress(address.getText().toString());
            buyer.setEmail(email.getText().toString());
            buyer.setQq(qq.getText().toString());
            buyer.setWechat(wechat.getText().toString());
            buyer.setWebsite(website.getText().toString());
            if(photoOutputUri!=null) {
                buyer.setPhoto(photoOutputUri.getPath());
            }
            buyer.save();
            Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
        }


    }




}
