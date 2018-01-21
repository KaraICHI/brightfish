package com.baosight.brightfish.ui.goods;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Goods;
import com.baosight.brightfish.ui.CameraBasicActivity;
import com.baosight.brightfish.ui.album.GoodsAblumActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewGoodsActivity extends CameraBasicActivity implements View.OnClickListener {
    ImageView photo;
    ImageView selectAblum;
    EditText sku, name, brand, catagory, size,  spec, descr;
    View color;
    TextView colorText;
    ImageView colorIcon;
    Button saveBtn;
    int cata;
    Dialog chooseDialog;
    GridView colorGridView;
    int colorPosition;
    int[] colorIcons=new int[]{R.drawable.rect_color_blue,R.drawable.rect_color_orange,R.drawable.rect_color_green,
            R.drawable.rect_color_pur,R.drawable.rect_color_yellow,R.drawable.rect_color_lblue,R.drawable.rect_color_white,
            R.drawable.rect_color_gery,R.drawable.rect_color_black,R.drawable.rect_color_red,
            R.drawable.rect_color_brown};
    String[] colorTexts;
    List<Map<String,Object>> colorList=new ArrayList<>();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_cata:
                showChooseDialog();
                break;
            case R.id.goods_color:
                showChooseColorDialog();
                break;
            case R.id.commit_cata:
                catagory.setText(cata);
                chooseDialog.dismiss();
                break;
            case R.id.cancel_btn:
                chooseDialog.dismiss();
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
        supplier.setColor(colorPosition);
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
        colorIcon.setVisibility(View.INVISIBLE);
        colorText.setText("");
        catagory.setText("");
        brand.setText("");

    }
    private void showChooseDialog() {
        chooseDialog=new Dialog(NewGoodsActivity.this,R.style.NoTitleDialog);
        chooseDialog.setContentView(R.layout.dialog_choose_cata);
        RadioGroup choosecataGroup=(RadioGroup) chooseDialog.findViewById(R.id.catas);
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
        Button commitcata=(Button) chooseDialog.findViewById(R.id.commit_cata);
        commitcata.setOnClickListener(this);
        Button cancelcata=(Button) chooseDialog.findViewById(R.id.cancel_btn);
        cancelcata.setOnClickListener(this);
        chooseDialog.show();
    }

}
