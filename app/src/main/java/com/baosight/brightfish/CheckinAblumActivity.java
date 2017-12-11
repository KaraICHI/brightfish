package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baosight.brightfish.model.AlbumItem;
import com.baosight.brightfish.ui.AlbumAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CheckinAblumActivity extends AlbumBasicActivity {


    public static void startCheckinAblumActivity(Context context) {
        Intent intent = new Intent(context, CheckinAblumActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_ablum);
        initToolbar(R.color.colorGreen);
        initControls();


    }


    private void initAblum() {
        albumItemList = new ArrayList<>();
        AlbumItem album = new AlbumItem();
        albumItemList.add(album);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkin_ablum, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void initControls() {
        initAblum();
        recyclerView = (RecyclerView) findViewById(R.id.add_Ablum_rec);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        adapter = new AlbumAdapter(albumItemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        addPhoto = (RelativeLayout) findViewById(R.id.add_photo);
        initCameraControls(addPhoto, null, null);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }

        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 通过返回码判断是哪个应用返回的数据
            switch (requestCode) {
                // 拍照
                case TAKE_PHOTO_REQUEST_CODE:
                    cropPhoto(photoUri);
                    break;
                // 裁剪图片
                case CROP_PHOTO_REQUEST_CODE:
                    File file = new File(photoOutputUri.getPath());
                    if (file.exists()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(photoOutputUri.getPath());
                        addAblum(bitmap);

//                      file.delete(); // 选取完后删除照片
                    } else {
                        Toast.makeText(this, "找不到照片", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private void addAblum(Bitmap bitmap) {
        AlbumItem albumItem = new AlbumItem(bitmap);
        albumItemList.add(albumItem);
        //adapter.notifyItemChanged(albumItemList.size()-1);
        adapter.notifyDataSetChanged();
    }

}


