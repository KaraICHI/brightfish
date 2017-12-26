package com.baosight.brightfish.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baosight.brightfish.R;
import com.baosight.brightfish.model.Account;
import com.baosight.brightfish.model.Goods;

import org.litepal.crud.DataSupport;


public class ModifyGoodsActivity extends EditActivity implements View.OnClickListener {
    EditText sku, name, brand, catagory, size, color, spec, descr;
    Goods goods;
    int cata;
    Dialog chooseCataDialog;
    private static final String TAG = "ModifyGoodsActivity";

    public static void startModifyActivity(Context context,Goods goods) {
        Intent intent = new Intent(context, ModifyGoodsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goods", goods);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_new);
        initControls();
        initToolbar(R.color.colorLightOrange);
        showGoods();
    }

    protected void initControls() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.blue_tooth_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
               showCameraDialog();
                //设置为空
                dialog.show();
                break;
            case R.id.select_ablum_btn:
                pictureImageView = photo;
                choiceFromAlbum();
                break;
            case R.id.checkin_commit:
               commit();

        }
    }

    private void showGoods(){
        goods=(Goods)getIntent().getBundleExtra("bundle").getSerializable("goods");
        sku.setText(goods.getSku());
        name.setText(goods.getName());
        brand.setText(goods.getBrand());
        catagory.setText(goods.getCatagory());
        spec.setText(goods.getSpec());
        size.setText(goods.getSize());
        color.setText(goods.getColor());
        descr.setText(goods.getDescr());
        Bitmap bitmap = BitmapFactory.decodeFile(goods.getPhoto());
        photo.setImageBitmap(bitmap);

    }

    private void updateGoods() {
        if (TextUtils.isEmpty(sku.getText()) || TextUtils.isEmpty(name.getText())) {
            setAlertDialog();
        }
        goods.setName(name.getText().toString());
        goods.setSku(sku.getText().toString());
        goods.setBrand(brand.getText().toString());
        goods.setCatagory(catagory.getText().toString());
        goods.setDescr(descr.getText().toString());
        goods.setColor(color.getText().toString());
        goods.setSize(size.getText().toString());
        goods.setSpec(spec.getText().toString());
        if(photoOutputUri!=null){
            goods.setPhoto(photoOutputUri.getPath());
        }
        goods.update(goods.getId());


    }
    private void showchooseCataDialog() {
        chooseCataDialog=new Dialog(ModifyGoodsActivity.this,R.style.NoTitleDialog);
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



    private void commit() {
        if (TextUtils.isEmpty(sku.getText()) || TextUtils.isEmpty(name.getText())) {
            setAlertDialog();
        } else if (DataSupport.find(Account.class, 1) != null) {
            updateGoods();
            Toast.makeText(ModifyGoodsActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
        }

    }


}
