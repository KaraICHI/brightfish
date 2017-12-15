package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class AnalyzeAllActivity extends BasicActivity{
    RadioGroup timeStandard,stockDater;
    LinearLayout defineTimeGroup;

    public static void startAnalyzeAllActivity(Context context) {
        Intent intent = new Intent(context, AnalyzeAllActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_all);
        initControls();
    }

    private void initControls() {
        initToolbar(R.color.colorPur);
        timeStandard=(RadioGroup) findViewById(R.id.time_standard);
        stockDater=(RadioGroup) findViewById(R.id.stock_dater);
        defineTimeGroup=(LinearLayout) findViewById(R.id.define_time_group);
        timeStandard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.standard_time:
                        defineTimeGroup.setVisibility(View.GONE);
                        break;
                    case R.id.define_time:
                        defineTimeGroup.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }
}
