package com.baosight.brightfish.ui.supplier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Supplier;
import com.baosight.brightfish.ui.EditActivity;
import com.baosight.brightfish.ui.album.SupplierAblumActivity;

public class NewSupplierActivity extends EditActivity implements View.OnClickListener {

    public static void startSupplierActivity(Context context) {
        Intent intent = new Intent(context, NewSupplierActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_new);
        initToolbar(R.color.colorGreen);
        initControls();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                showCameraDialog();
                break;
            case R.id.select_ablum_btn:
                SupplierAblumActivity.startSupplierAblumActivity(this);
                break;
            case R.id.commit_edit:
                saveSupplier();
                clearEditText();

        }
    }
    private void saveSupplier(){
        if (TextUtils.isEmpty(sku.getText())||TextUtils.isEmpty(name.getText())) {
            setAlertDialog();
        }else {
            Supplier supplier=new Supplier();
            supplier.setName(name.getText().toString());
            supplier.setSku(sku.getText().toString());
            supplier.setCellphone(cellphone.getText().toString());
            supplier.setTelephone(telephone.getText().toString());
            supplier.setDescr(descr.getText().toString());
            supplier.setAddress(address.getText().toString());
            supplier.setEmail(email.getText().toString());
            supplier.setQq(qq.getText().toString());
            supplier.setWechat(wechat.getText().toString());
            supplier.setWebsite(website.getText().toString());
            if(photoOutputUri!=null) {
                supplier.setPhoto(photoOutputUri.getPath());
            }
            supplier.save();
            Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
        }


    }


}
