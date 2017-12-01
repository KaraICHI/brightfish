package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AccountActivity extends AppCompatActivity {
    public static void startAccountActivity(Context context) {
        Intent intent = new Intent(context, AccountActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }
}
