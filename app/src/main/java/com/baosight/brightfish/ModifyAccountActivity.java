package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.baosight.brightfish.model.Account;

import org.litepal.crud.DataSupport;


public class ModifyAccountActivity extends EditActivity {
    Account account;

    public static void startModifyAccountActivity(Context context) {
        Intent intent = new Intent(context, ModifyAccountActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_account);
        initToolbar(R.color.colorLightGery);
        initControls();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                showCameraDialog();
                break;
            case R.id.select_ablum_btn:
                pictureImageView = photo;
                choiceFromAlbum();
                break;
            case R.id.commit_edit:
                commit();
                break;
            default:
                break;

        }
    }

    private void initAccount() {
        account = new Account();
        account.setName(name.getText().toString());
        account.setSku(sku.getText().toString());
        account.setCellphoto(cellphone.getText().toString());
        account.setTelephone(telephone.getText().toString());
        account.setDescr(descr.getText().toString());
        account.setAddress(address.getText().toString());
        account.setEmail(email.getText().toString());
        account.setQq(qq.getText().toString());
        account.setWechat(wechat.getText().toString());
        account.setWebsite(website.getText().toString());
        if(photoOutputUri!=null){
            account.setPhoto(photoOutputUri.getPath());
        }

    }


    private void commit() {
        if (TextUtils.isEmpty(sku.getText()) || TextUtils.isEmpty(name.getText())) {
            setAlertDialog();
        } else if (DataSupport.find(Account.class, 1) != null) {
            initAccount();
            account.update(1);
            Toast.makeText(ModifyAccountActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
        } else {
            initAccount();
            account.save();
            Toast.makeText(ModifyAccountActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
        }

        clearEditText();
    }


}
