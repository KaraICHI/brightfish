package com.baosight.brightfish;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingActivity extends BasicActivity implements View.OnClickListener{
    LinearLayout blueTooth,scanner,database;
    private final int REQUEST_ENABLE_BT=1;

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
        blueTooth=(LinearLayout) findViewById(R.id.blueTooth);
        scanner=(LinearLayout) findViewById(R.id.scan_code);
        database=(LinearLayout) findViewById(R.id.database);
        blueTooth.setOnClickListener(this);
        scanner.setOnClickListener(this);
        database.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.blueTooth:
                openBlueTooth();
                break;

        }
    }

    private void openBlueTooth() {

        BluetoothAdapter mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter==null){
            Toast.makeText(SettingActivity.this,"该设备不支持蓝牙",Toast.LENGTH_SHORT).show();
        }else if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode==RESULT_OK){
                    //
                }
        }
    }
}
