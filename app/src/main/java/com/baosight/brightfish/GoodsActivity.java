package com.baosight.brightfish;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;



public class GoodsActivity extends BasicActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageView photo;
    ImageView selectAblum;

    public static void startGoodsActivity(Context context) {
        Intent intent = new Intent(context, GoodsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initControls();
    }

    private void initControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        photo = (ImageView) findViewById(R.id.photo);
        assert photo != null;
        photo.setOnClickListener(this);
        selectAblum = (ImageView) findViewById(R.id.select_ablum_btn);
        assert selectAblum != null;
        selectAblum.setOnClickListener(this);
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
                //设置为空

                dialog.show();
                break;
            case R.id.select_ablum_btn:
                GoodsAblumActivity.startGoodsAblumActivity(GoodsActivity.this);
                break;

        }
    }

}
