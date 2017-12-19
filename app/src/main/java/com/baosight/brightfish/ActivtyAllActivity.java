package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baosight.brightfish.model.Checkin;
import com.baosight.brightfish.model.Checkout;
import com.baosight.brightfish.ui.RecentCheckinAdapter;
import com.baosight.brightfish.ui.RecentCheckoutAdapter;
import com.baosight.brightfish.util.CurrentTime;

import org.litepal.crud.DataSupport;

public class ActivtyAllActivity extends BasicActivity {
    RecyclerView recyclerViewCheckin,recyclerViewCheckout;
    RecentCheckinAdapter adapterIn;
    RecentCheckoutAdapter adapterOut;
    Button allCheckin,allCheckout;
    TextView updateTime;


    public static void startActivityAllActivity(Context context) {
        Intent intent = new Intent(context, ActivtyAllActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activty_all);
        initControls();
    }

    private void initControls() {
        initToolbar(R.color.colorPur);
        recyclerViewCheckin=(RecyclerView) findViewById(R.id.recent_checkin_rec);
        recyclerViewCheckout=(RecyclerView) findViewById(R.id.recent_checkout_rec);
        recyclerViewCheckin.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCheckout.setLayoutManager(new LinearLayoutManager(this));
        adapterIn=new RecentCheckinAdapter(DataSupport.where("id<?",7+"").find(Checkin.class));
        adapterOut=new RecentCheckoutAdapter(DataSupport.where("id<?",7+"").find(Checkout.class));
        recyclerViewCheckin.setAdapter(adapterIn);
        recyclerViewCheckout.setAdapter(adapterOut);
        updateTime=(TextView) findViewById(R.id.update_time);
        allCheckin = (Button) findViewById(R.id.all_checkin);
        allCheckout = (Button)findViewById(R.id.all_checkout);
        allCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecentCheckinActivity.startRecentCheckinActivity(ActivtyAllActivity.this, DataSupport.findAll(Checkin.class));
            }
        });
        allCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecentCheckoutActivity.startRecentCheckoutActivity(ActivtyAllActivity.this,DataSupport.findAll(Checkout.class));
            }
        });
        updateTime.setText("更新时间："+new CurrentTime().getHMTime());

    }
}
