package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * 查找入库
 */
public class SearchCheckinActivity extends SearchBasicActivity implements View.OnClickListener {


    public static void startSearchCheckinActivity(Context context) {
        Intent intent = new Intent(context, SearchCheckinActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_checkin);
        initControls(R.layout.item_add_condition_in);
        initToolbar(R.color.colorGreen);

    }


}
