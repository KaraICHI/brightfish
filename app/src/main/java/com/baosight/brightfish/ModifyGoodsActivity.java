package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baosight.brightfish.model.Account;
import com.baosight.brightfish.model.Goods;

import org.litepal.crud.DataSupport;


public class ModifyGoodsActivity extends EditActivity implements View.OnClickListener {
    EditText sku, name, brand, catagory, size, color, spec, descr;
    Goods goods;
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


    protected void clearEditText() {
        sku.setText("");
        name.setText("");
        spec.setText("");
        descr.setText("");
        size.setText("");
        color.setText("");
        catagory.setText("");
        brand.setText("");

    }

    private void commit() {
        if (TextUtils.isEmpty(sku.getText()) || TextUtils.isEmpty(name.getText())) {
            setAlertDialog();
        } else if (DataSupport.find(Account.class, 1) != null) {
            updateGoods();
            Toast.makeText(ModifyGoodsActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
        }

        clearEditText();
    }


}
