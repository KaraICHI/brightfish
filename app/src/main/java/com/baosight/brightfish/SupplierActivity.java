package com.baosight.brightfish;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class SupplierActivity extends BasicActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageView photo;
    ImageView selectAblum;

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

        }
    }


}
