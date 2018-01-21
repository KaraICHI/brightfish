package com.baosight.brightfish.ui.goods;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Account;
import com.baosight.brightfish.domain.Goods;
import com.baosight.brightfish.ui.EditActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ModifyGoodsActivity extends EditActivity implements View.OnClickListener {
    EditText sku, name, brand, catagory, size,  spec, descr;
    Goods goods;
    int cata,colorPosition;
    View color;
    TextView colorText;
    ImageView colorIcon;
    Dialog chooseCataDialog;
    Dialog chooseDialog;
    GridView colorGridView;
    int[] colorIcons=new int[]{R.drawable.rect_color_blue,R.drawable.rect_color_orange,R.drawable.rect_color_green,
            R.drawable.rect_color_pur,R.drawable.rect_color_yellow,R.drawable.rect_color_lblue,R.drawable.rect_color_white,
            R.drawable.rect_color_gery,R.drawable.rect_color_black,R.drawable.rect_color_red,
            R.drawable.rect_color_brown};
    String[] colorTexts;
    List<Map<String,Object>> colorList=new ArrayList<>();
    private static final String TAG = "ModifyGoodsActivity";

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
        color =  findViewById(R.id.goods_color);
        colorIcon= (ImageView) findViewById(R.id.color_icon);
        colorText= (TextView) findViewById(R.id.color_text);
        spec = (EditText) findViewById(R.id.goods_spec);
        descr = (EditText) findViewById(R.id.goods_descr);
        saveBtn = (Button) findViewById(R.id.checkin_commit);
        saveBtn.setOnClickListener(this);
        catagory.setOnClickListener(this);
        color.setOnClickListener(this);
        initColorList();
    }

    void initColorList(){
        //   int[] colorIcons=getResources().getIntArray(R.array.color_list);
        colorTexts=getResources().getStringArray(R.array.color_text_list);
        Map<String,Object> colorMap1=new HashMap<>();
        colorMap1.put("text","未确定");
        colorList.add(colorMap1);
        for (int i=0;i<colorIcons.length&&i<colorTexts.length;i++){
            Map<String,Object> colorMap=new HashMap<>();
            colorMap.put("icon",colorIcons[i]);
            colorMap.put("text",colorTexts[i]);
            colorList.add(colorMap);
        }

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
            case R.id.goods_color:
                showChooseColorDialog();
                break;
            case R.id.commit_cata:
                catagory.setText(cata);
                chooseCataDialog.dismiss();
                break;
            case R.id.cancel_btn:
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

    private void showChooseColorDialog() {

        SimpleAdapter simpleAdapter=new SimpleAdapter(this,colorList,R.layout.item_color,new String[]{"icon","text"},new int[]{R.id.color_icon,R.id.color_text});
        chooseDialog=new Dialog(this,R.style.NoTitleDialog);
        chooseDialog.setContentView(R.layout.dialog_choose_color);
        colorGridView= (GridView) chooseDialog.findViewById(R.id.color_list);
        colorGridView.setAdapter(simpleAdapter);
        Button cancel= (Button) chooseDialog.findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(this);
        colorGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position>0){
                    colorIcon.setVisibility(View.VISIBLE);
                    colorIcon.setImageResource(colorIcons[position-1]);
                    colorText.setText(colorTexts[position-1]);
                }else {
                    colorIcon.setVisibility(View.INVISIBLE);
                    colorText.setText("");
                }
                colorPosition=position-1;
                chooseDialog.dismiss();
            }
        });
        chooseDialog.show();

    }

    private void showGoods(){
        goods=(Goods)getIntent().getBundleExtra("bundle").getSerializable("goods");
        sku.setText(goods.getSku());
        name.setText(goods.getName());
        brand.setText(goods.getBrand());
        catagory.setText(goods.getCatagory());
        spec.setText(goods.getSpec());
        size.setText(goods.getSize());
        if(goods.getColor()>=0){
            colorIcon.setImageResource(colorIcons[goods.getColor()]);
            colorText.setText(colorTexts[goods.getColor()]);
        }
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
        goods.setColor(colorPosition);
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
        Button cancelcata=(Button) chooseCataDialog.findViewById(R.id.cancel_btn);
        cancelcata.setOnClickListener(this);
        chooseCataDialog.show();
    }



    private void commit() {
        try{
            updateGoods();
            Toast.makeText(ModifyGoodsActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("goods", goods);
            intent.putExtra("bundle", bundle);
            setResult(Activity.RESULT_OK, intent);
            finish();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(ModifyGoodsActivity.this, "更新失败", Toast.LENGTH_SHORT).show();

        }



    }


}
