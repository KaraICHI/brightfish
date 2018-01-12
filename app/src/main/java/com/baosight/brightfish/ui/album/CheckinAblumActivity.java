package com.baosight.brightfish.ui.album;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.AlbumItem;

import java.io.File;

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
        setCropSize();

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkin_ablum, menu);
        return super.onCreateOptionsMenu(menu);
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


