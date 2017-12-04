package com.baosight.brightfish;

import android.app.Dialog;
import android.bluetooth.le.AdvertiseData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.baosight.brightfish.model.Supplier;

import java.io.File;
import java.io.IOException;

public class SupplierActivity extends BasicActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageView photo;
    ImageView selectAblum;
    EditText sku,name,address,cellphone,telephone,email,wechat,qq,descr,website;
    Button saveBtn;
    public static void startSupplierActivity(Context context) {
        Intent intent = new Intent(context, SupplierActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        initControls();
    }

    private void initControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        saveBtn=(Button) findViewById(R.id.supplier_commit);
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
                LinearLayout takePhoto = (LinearLayout) dialog.findViewById(R.id.take_photo);
                LinearLayout openAblum = (LinearLayout) dialog.findViewById(R.id.open_ablum);
                LinearLayout setNull = (LinearLayout) dialog.findViewById(R.id.set_photo_null);
                initCameraControls(takePhoto, openAblum, photo, dialog, setNull);
                dialog.show();
                break;
            case R.id.select_ablum_btn:
                SupplierAblumActivity.startSupplierAblumActivity(this);
                break;
            case R.id.supplier_commit:
                saveSupplier();
                Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
                clearEditText();

        }
    }
    private void saveSupplier(){
        if (TextUtils.isEmpty(sku.getText())||TextUtils.isEmpty(name.getText())) {
            setAlertDialog();
        }
        Supplier supplier=new Supplier();
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
        AlertDialog.Builder dialog = new AlertDialog.Builder(SupplierActivity.this);
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
