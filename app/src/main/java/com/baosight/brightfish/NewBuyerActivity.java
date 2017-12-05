package com.baosight.brightfish;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baosight.brightfish.model.Buyer;

public class NewBuyerActivity extends BasicActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageView photo;
    ImageView selectAblum;
    EditText sku,name,address,cellphone,telephone,email,wechat,qq,descr,website;
    Button saveBtn;
    public static void startBuyerActivity(Context context) {
        Intent intent = new Intent(context, NewBuyerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        initToolbar();
        initControls();

    }
    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*
     *控件初始化
     */
    private void initControls() {
        photo = (ImageView) findViewById(R.id.photo);
        photo.setOnClickListener(this);
        selectAblum = (ImageView) findViewById(R.id.select_ablum_btn);
        selectAblum.setOnClickListener(this);
        sku=(EditText) findViewById(R.id.supplier_sku_checkin);
        name=(EditText) findViewById(R.id.supplier_name_checkin);
        address=(EditText) findViewById(R.id.supplier_address);
        telephone=(EditText) findViewById(R.id.supplier_telephone);
        cellphone=(EditText)findViewById(R.id.supplier_cellphone);
        email=(EditText) findViewById(R.id.supplier_email);
        descr=(EditText) findViewById(R.id.supplier_describe);
        qq=(EditText) findViewById(R.id.supplier_qq);
        wechat=(EditText) findViewById(R.id.supplier_wecaht);
        website=(EditText) findViewById(R.id.supplier_web);
        saveBtn=(Button) findViewById(R.id.checkin_commit);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.blue_tooth_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                final Dialog dialog = new Dialog(this, R.style.NoTitleDialog);
                dialog.setContentView(R.layout.layout_dialog);
                dialog.setCanceledOnTouchOutside(true);
                LinearLayout takePhoto = (LinearLayout) dialog.getWindow().findViewById(R.id.take_photo);
                LinearLayout openAblum = (LinearLayout) dialog.getWindow().findViewById(R.id.open_ablum);
                LinearLayout setNull = (LinearLayout) dialog.findViewById(R.id.set_photo_null);
                initCameraControls(takePhoto, openAblum, photo, dialog, setNull);
                dialog.show();

                break;
            case R.id.select_ablum_btn:
                BuyerAblumActivity.startBuyerAblumActivity(NewBuyerActivity.this);
                break;
            case R.id.checkin_commit:
                saveBuyer();
                Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
                clearEditText();
        }
    }
    private void saveBuyer(){
        if (TextUtils.isEmpty(sku.getText())||TextUtils.isEmpty(name.getText())) {
            setAlertDialog();
        }
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

    }
    private void setAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(NewBuyerActivity.this);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setMessage("sku或名称不能为空");

        dialog.show();
    }
    private void clearEditText(){
        sku.setText("");
        name.setText("");
        address.setText("");
        descr.setText("");
        cellphone.setText("");
        telephone.setText("");
        email.setText("");
        wechat.setText("");
        qq.setText("");
        website.setText("");
    }



}
