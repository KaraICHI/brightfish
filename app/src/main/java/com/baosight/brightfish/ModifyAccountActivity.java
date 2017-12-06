package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.baosight.brightfish.model.Buyer;

import org.litepal.tablemanager.Connector;

public class ModifyAccountActivity extends EditActivity {
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
                SupplierAblumActivity.startSupplierAblumActivity(this);
                break;
            case R.id.commit_edit:
                clearEditText();

        }
    }





}
