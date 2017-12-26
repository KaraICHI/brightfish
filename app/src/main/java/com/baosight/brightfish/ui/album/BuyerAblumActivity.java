package com.baosight.brightfish.ui.album;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baosight.brightfish.R;
import com.baosight.brightfish.model.AlbumItem;

import java.io.File;
import java.util.ArrayList;

public class BuyerAblumActivity extends AlbumBasicActivity {

    public static void startBuyerAblumActivity(Context context) {
        Intent intent = new Intent(context, BuyerAblumActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_ablum);
        initControls();
        initToolbar(R.color.colorBlue);
        setCropSize();
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
        recyclerView = (RecyclerView) findViewById(R.id.add_Ablum_rec);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        initAblum();
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
