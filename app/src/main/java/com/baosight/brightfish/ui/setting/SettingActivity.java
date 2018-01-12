package com.baosight.brightfish.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.baosight.brightfish.R;
import com.baosight.brightfish.ui.BasicActivity;

public class SettingActivity extends BasicActivity implements View.OnClickListener {
    LinearLayout blueTooth, scanner, database;


    public static void startSettingActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initToolbar(R.color.colorHome);
        initControls();
    }

    private void initControls() {
        blueTooth = (LinearLayout) findViewById(R.id.blueTooth);
        scanner = (LinearLayout) findViewById(R.id.scan_code);
        database = (LinearLayout) findViewById(R.id.database);
        blueTooth.setOnClickListener(this);
        scanner.setOnClickListener(this);
        database.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.blueTooth:
                showBlueToothDialog();
                break;

        }
    }


}
