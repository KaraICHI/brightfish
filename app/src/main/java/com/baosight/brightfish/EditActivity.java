package com.baosight.brightfish;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * Created by Administrator on 2017/12/5.
 */

public class EditActivity extends BasicActivity implements View.OnClickListener{
    Toolbar toolbar;
    ImageView photo;
    ImageView selectAblum;
    EditText sku,name,address,cellphone,telephone,email,wechat,qq,descr,website;
    Button saveBtn;

    protected void initToolbar(int color){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(color));
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

    protected void initControls() {
        photo = (ImageView) findViewById(R.id.photo);
        photo.setOnClickListener(this);
        selectAblum = (ImageView) findViewById(R.id.select_ablum_btn);
        selectAblum.setOnClickListener(this);
        sku=(EditText) findViewById(R.id.sku_edit);
        name=(EditText) findViewById(R.id.name_edit);
        address=(EditText) findViewById(R.id.address_edit);
        telephone=(EditText) findViewById(R.id.telephone_edit);
        cellphone=(EditText)findViewById(R.id.cellphone_edit);
        email=(EditText) findViewById(R.id.email_edit);
        descr=(EditText) findViewById(R.id.describe_edit);
        qq=(EditText) findViewById(R.id.qq_edit);
        wechat=(EditText) findViewById(R.id.wechat_edit);
        website=(EditText) findViewById(R.id.web_edit);
        saveBtn=(Button) findViewById(R.id.commit_edit);
        if(saveBtn!=null){
            saveBtn.setOnClickListener(this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.blue_tooth_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    protected void setAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(EditActivity.this);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setMessage("sku或名称不能为空");

        dialog.show();
    }
    protected void clearEditText(){
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
    protected void showCameraDialog(){
        final Dialog dialog = new Dialog(this, R.style.NoTitleDialog);
        dialog.setContentView(R.layout.layout_dialog);
        dialog.setCanceledOnTouchOutside(true);
        LinearLayout takePhoto = (LinearLayout) dialog.findViewById(R.id.take_photo);
        LinearLayout openAblum = (LinearLayout) dialog.findViewById(R.id.open_ablum);
        LinearLayout setNull = (LinearLayout) dialog.findViewById(R.id.set_photo_null);
        initCameraControls(takePhoto, openAblum, photo, dialog, setNull);
        dialog.show();

    }

    @Override
    public void onClick(View v) {

    }
}
