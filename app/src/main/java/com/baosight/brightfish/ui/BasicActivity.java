package com.baosight.brightfish.ui;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Buyer;
import com.baosight.brightfish.domain.Checkin;
import com.baosight.brightfish.domain.Checklist;
import com.baosight.brightfish.domain.Checkout;
import com.baosight.brightfish.domain.Goods;
import com.baosight.brightfish.domain.Supplier;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;

import org.litepal.crud.DataSupport;

import java.util.Set;


/**
 * Created by Administrator on 2017/12/7.
 */

public class BasicActivity extends AppCompatActivity {
    private final int REQUEST_ENABLE_BT = 5;
    protected RelativeLayout currentSortMethod;
    protected boolean sortdesc;
    Toolbar toolbar;
    private Dialog mBlueToothDialog;
    private Switch mBluetoothSwith;
    private ListView mBlueToothList;
    private ArrayAdapter<String> mArrayAdapter;
    private LinearLayout mShowDevices;


    protected void initToolbar(int color) {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setBackgroundColor(getResources().getColor(color));
    }

    protected SwipeMenuCreator getSlideMenuCreator() {


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                openItem.setWidth(240);
                openItem.setTitle("Open");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(240);
                deleteItem.setTitle("Delete");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        return creator;
    }

    protected void deleteGoods(int goodsId) {
        DataSupport.delete(Goods.class, goodsId);
        DataSupport.deleteAll(Checkin.class, "goodsId=?", goodsId + "");
        DataSupport.deleteAll(Checkout.class, "goodsId=?", goodsId + "");
        DataSupport.deleteAll(Checklist.class, "goodsId=?", goodsId + "");
    }

    protected void deleteBuyer(int buyerId) {
        DataSupport.delete(Buyer.class, buyerId);
        DataSupport.deleteAll(Checkout.class, "goodsId=?", buyerId + "");
    }

    protected void deleteSuppiler(int supplierId) {
        DataSupport.delete(Supplier.class, supplierId);
        DataSupport.deleteAll(Checkin.class, "goodsId=?", supplierId + "");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.blue_tooth_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.blueTooth:
                showBlueToothDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void showBlueToothDialog() {
        mBlueToothDialog = new Dialog(this, R.style.NoTitleDialog);
        mBlueToothDialog.setContentView(R.layout.dialog_bluetooth);
        mBluetoothSwith = (Switch) mBlueToothDialog.findViewById(R.id.bluetooth_swith);
        mShowDevices=(LinearLayout) mBlueToothDialog.findViewById(R.id.show_bluetooth);
        mArrayAdapter= new ArrayAdapter<>(this, R.layout.item_bluetooth);
        mBlueToothList=(ListView) mBlueToothDialog.findViewById(R.id.bluetooth_devices_list);
        Button cancelBtn = (Button) mBlueToothDialog.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBlueToothDialog.dismiss();
            }
        });
        mBluetoothSwith.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    openBlueTooth(mArrayAdapter);
                    mBlueToothList.setAdapter(mArrayAdapter);
                }else {
                    mBluetoothSwith.setText("关闭");
                    mShowDevices.setVisibility(View.GONE);
                }
            }
        });

        mBlueToothDialog.show();
    }

    protected void openBlueTooth(final ArrayAdapter<String> mArrayAdapter) {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(BasicActivity.this, "该设备不支持蓝牙", Toast.LENGTH_SHORT).show();
        } else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
        final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
        };
// Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    mBluetoothSwith.setText("已开启");
                    mShowDevices.setVisibility(View.VISIBLE);
                }
        }
    }


}
