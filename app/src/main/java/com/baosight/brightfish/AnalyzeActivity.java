package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AnalyzeActivity extends AppCompatActivity {
    public static void startAnalyzeActivity(Context context) {
        Intent intent = new Intent(context, AnalyzeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);
    }
}
