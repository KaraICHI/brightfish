package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

public class GoodsAblumActivity extends CameraBasicActivity {

    Toolbar toolbar;

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
    }

    private void initControls() {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkin_ablum, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
