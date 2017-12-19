package com.baosight.brightfish;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baosight.brightfish.model.Checkin;
import com.baosight.brightfish.model.Checkout;
import com.baosight.brightfish.util.CurrentTime;

import org.litepal.crud.DataSupport;

public class AnalyzeAllActivity extends BasicActivity implements View.OnClickListener{
    RadioGroup timeStandard,stockDater;
    LinearLayout defineTimeGroup;

    EditText startTime,endTime;
    Button analyzeActivityBtn;


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
        analyzeActivityBtn=(Button) findViewById(R.id.analyze_activity);
        analyzeActivityBtn.setOnClickListener(this);
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
        startTime=(EditText) findViewById(R.id.start_time);
        endTime=(EditText) findViewById(R.id.end_time);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        startTime.setText(new CurrentTime().getYMDTime(6));
        endTime.setText(new CurrentTime().getYMDTime(0));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_time:
              new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        startTime.setText(year+" "+(monthOfYear+1)+"月 "+dayOfMonth);
                    }
                },2017,5,1).show();
                break;
            case R.id.end_time:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        endTime.setText(year+" "+(monthOfYear+1)+"月 "+dayOfMonth);
                    }
                },2017,11,18).show();
                break;
            case R.id.analyze_activity:
                ActivtyAllActivity.startActivityAllActivity(this);
                break;
        }
    }
}
