package com.baosight.brightfish;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baosight.brightfish.model.Goods;


public class NewGoodsActivity extends CameraBasicActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageView photo;
    ImageView selectAblum;
    EditText sku, name, brand, catagory, size, color, spec, descr;
    Button saveBtn;
    int cata;
    Dialog chooseCataDialog;
    public static void startGoodsActivity(Context context) {
        Intent intent = new Intent(context, NewGoodsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_new);
        initControls();
        initToolbar(R.color.colorOrange);
    }

    private void initControls() {
        photo = (ImageView) findViewById(R.id.photo);
        assert photo != null;
        photo.setOnClickListener(this);
        selectAblum = (ImageView) findViewById(R.id.select_ablum_btn);
        assert selectAblum != null;
        selectAblum.setOnClickListener(this);
        sku = (EditText) findViewById(R.id.goods_sku);
        name = (EditText) findViewById(R.id.goods_name);
        brand = (EditText) findViewById(R.id.goods_brand);
        catagory = (EditText) findViewById(R.id.goods_cata);
        size = (EditText) findViewById(R.id.goods_size);
        color = (EditText) findViewById(R.id.goods_color);
        spec = (EditText) findViewById(R.id.goods_spec);
        descr = (EditText) findViewById(R.id.goods_descr);
        saveBtn = (Button) findViewById(R.id.checkin_commit);
        saveBtn.setOnClickListener(this);
        catagory.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_cata:
                showchooseCataDialog();
                break;
            case R.id.commit_cata:
                catagory.setText(cata);
                chooseCataDialog.dismiss();
                break;
            case R.id.cancle_cata:
                chooseCataDialog.dismiss();
                break;
            case R.id.photo:
                final Dialog dialog = new Dialog(this, R.style.NoTitleDialog);
                dialog.setContentView(R.layout.layout_dialog);
                dialog.setCanceledOnTouchOutside(true);
                LinearLayout takePhoto = (LinearLayout) dialog.getWindow().findViewById(R.id.take_photo);
                LinearLayout openAblum = (LinearLayout) dialog.getWindow().findViewById(R.id.open_ablum);
                LinearLayout setNull = (LinearLayout) dialog.findViewById(R.id.set_photo_null);
                initCameraControls(takePhoto, openAblum, photo, dialog, setNull);
                //设置为空

                dialog.show();
                break;
            case R.id.select_ablum_btn:
                GoodsAblumActivity.startGoodsAblumActivity(NewGoodsActivity.this);
                break;
            case R.id.checkin_commit:
                saveGoods();
                Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT).show();
                clearEditText();

        }
    }

    private void saveGoods() {
        if (TextUtils.isEmpty(sku.getText()) || TextUtils.isEmpty(name.getText())) {
            setAlertDialog();
        }
        Goods supplier = new Goods();
        supplier.setName(name.getText().toString());
        supplier.setSku(sku.getText().toString());
        supplier.setBrand(brand.getText().toString());
        supplier.setCatagory(catagory.getText().toString());
        supplier.setDescr(descr.getText().toString());
        supplier.setColor(color.getText().toString());
        supplier.setSize(size.getText().toString());
        supplier.setSpec(spec.getText().toString());
        if(photoOutputUri!=null){
            supplier.setPhoto(photoOutputUri.getPath());
        }
        supplier.save();

    }

    private void setAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(NewGoodsActivity.this);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setMessage("sku或名称不能为空");

        dialog.show();
    }

    private void clearEditText() {
        sku.setText("");
        name.setText("");
        spec.setText("");
        descr.setText("");
        size.setText("");
        color.setText("");
        catagory.setText("");
        brand.setText("");

    }
    private void showchooseCataDialog() {
        chooseCataDialog=new Dialog(NewGoodsActivity.this,R.style.NoTitleDialog);
        chooseCataDialog.setContentView(R.layout.dialog_choose_cata);
        RadioGroup choosecataGroup=(RadioGroup) chooseCataDialog.findViewById(R.id.catas);
        choosecataGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.cata_product:
                        cata=R.string.product;
                        break;
                    case R.id.cata_material:
                        cata=R.string.material;
                        break;
                    case R.id.cata_service:
                        cata=R.string.service;
                        break;
                    case R.id.cata_retail:
                        cata=R.string.retail;
                        break;
                    case R.id.cata_device:
                        cata=R.string.device;
                        break;
                    default:
                        break;

                }
            }
        });
        Button commitcata=(Button) chooseCataDialog.findViewById(R.id.commit_cata);
        commitcata.setOnClickListener(this);
        Button cancelcata=(Button) chooseCataDialog.findViewById(R.id.cancle_cata);
        cancelcata.setOnClickListener(this);
        chooseCataDialog.show();
    }

}
