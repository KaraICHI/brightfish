package com.baosight.brightfish.ui.album;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.baosight.brightfish.R;

public class GoodsAblumActivity extends AlbumBasicActivity {


    public static void startGoodsAblumActivity(Context context) {
        Intent intent = new Intent(context, GoodsAblumActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_ablum);
        initControls();
        initToolbar(R.color.colorOrange);
        setCropSize();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkin_ablum, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
