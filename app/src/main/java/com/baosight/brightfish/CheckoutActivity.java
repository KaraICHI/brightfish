package com.baosight.brightfish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baosight.brightfish.model.Buyer;
import com.baosight.brightfish.model.Goods;
import com.baosight.brightfish.model.Supplier;

import java.util.Calendar;
import java.util.TimeZone;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int msgKey1 = 1;
    Toolbar toolbar;
    private String content = "";
    private boolean isFocus;
    TextView goodsText, note, buyerText, currentTime;
    Button commit;
    EditText goodsSku, goodsName, price, amount, description, supplierSku, supplierName;
    ImageButton goodsMenu, goodsRefesh, goodsPhoto, buyerMenu, buyerRefesh;
    Goods goods;
    Buyer buyer;

    public static void startCheckoutActivity(Context context) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        initControls();
    }

    private void initControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        goodsText = (TextView) findViewById(R.id.check_good);
        note = (TextView) findViewById(R.id.note_checkin);
        buyerText = (TextView) findViewById(R.id.check_buyer);
        currentTime = (TextView) findViewById(R.id.current_time);
        goodsText.setTextColor(getResources().getColor(R.color.colorBlue));
        note.setTextColor(getResources().getColor(R.color.colorBlue));
        currentTime.setTextColor(getResources().getColor(R.color.colorBlue));
        currentTime.setTextColor(getResources().getColor(R.color.colorBlue));
        currentTime = (TextView) findViewById(R.id.current_time);
        new TimeThread().start();
        commit = (Button) findViewById(R.id.checkin_commit);
        assert commit != null;
        commit.setOnClickListener(this);
        goodsSku = (EditText) findViewById(R.id.goods_sku_checkin);
        goodsName = (EditText) findViewById(R.id.goods_name_checkin);
        price = (EditText) findViewById(R.id.price_checkin);
        amount = (EditText) findViewById(R.id.amount_checkin);
        description = (EditText) findViewById(R.id.descripe_checkin);
        supplierName = (EditText) findViewById(R.id.buyer_name_checkin);
        supplierSku = (EditText) findViewById(R.id.supplier_sku_checkin);
        goodsMenu = (ImageButton) findViewById(R.id.check_good_menu);
        goodsRefesh = (ImageButton) findViewById(R.id.check_good_refesh);
        buyerMenu = (ImageButton) findViewById(R.id.check_buyer_menu);
        buyerRefesh = (ImageButton) findViewById(R.id.check_buyer_refesh);
        goodsPhoto = (ImageButton) findViewById(R.id.photo_goods);
        goodsPhoto.setOnClickListener(this);
        goodsName.setOnClickListener(this);
        goodsSku.setOnClickListener(this);
        price.setOnClickListener(this);
        amount.setOnClickListener(this);
        description.setOnClickListener(this);
        goodsMenu.setOnClickListener(this);
        goodsRefesh.setOnClickListener(this);
        buyerMenu.setOnClickListener(this);
        buyerRefesh.setOnClickListener(this);
        goodsMenu.setBackgroundResource(R.drawable.ic_list_blue_24dp);
        buyerMenu.setBackgroundResource(R.drawable.ic_list_blue_24dp);
        goodsRefesh.setBackgroundResource(R.drawable.ic_refresh_blue_24dp);
        buyerRefesh.setBackgroundResource(R.drawable.ic_refresh_blue_24dp);
        goodsPhoto.setBackgroundResource(R.drawable.ic_image_blue_24dp);


    }

    @Override
    public void onClick(View v) {
        if (v.getClass() == android.support.v7.widget.AppCompatEditText.class) {
            content += goodsSku.getText();
            isFocus = true;
            invalidateOptionsMenu();
        }
        switch (v.getId()) {
            case R.id.check_good_menu:
                Intent intent = new Intent(this, ChooseGoodsActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.check_good_refesh:
                goodsSku.setText("");
                goodsName.setText("");
                break;
            case R.id.check_buyer_menu:
                Intent intent2 = new Intent(this, ChooseBuyerActivity.class);
                startActivityForResult(intent2, 1);
                break;
            case R.id.check_buyer_refesh:
                supplierSku.setText("");
                supplierName.setText("");
                break;
            case R.id.photo_goods:
                CheckinAblumActivity.startCheckinAblumActivity(CheckoutActivity.this);
                break;
            case R.id.checkin_commit:
                setAlertDialog();
                break;

        }
    }

    private void setAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(CheckoutActivity.this);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if (TextUtils.isEmpty(content)) {
            dialog.setMessage("内容不能为空");
        } else if (TextUtils.isEmpty(goodsName.getText())) {
            dialog.setMessage("货物不能为空");
        } else if (TextUtils.isEmpty(price.getText())) {
            dialog.setMessage("价格不能为空");
        } else if (TextUtils.isEmpty(amount.getText())) {
            dialog.setMessage("数量不能为空");
        }
        dialog.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!isFocus) {
            menu.findItem(R.id.check_mark).setVisible(false);
        } else {
            menu.findItem(R.id.check_mark).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check_mark:
                setAlertDialog();
                break;
            case R.id.blueTooth:
                Toast.makeText(CheckoutActivity.this, "开启蓝牙扫码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.default_param:
                Toast.makeText(CheckoutActivity.this, "货品默认参数", Toast.LENGTH_SHORT).show();
                break;
            case R.id.kuaiSao:
                Toast.makeText(CheckoutActivity.this, "开启快扫模式", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkin_bar_menu, menu);
        return true;
    }


    //显示时间线程
    public class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    currentTime.setText(getTime());
                    break;
                default:
                    break;
            }
        }
    };

    //获得当前年月日时分秒星期
    public String getTime() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        String mHour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));//时
        String mMinute = String.valueOf(c.get(Calendar.MINUTE));//分
        String mSecond = String.valueOf(c.get(Calendar.SECOND));//秒
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return mYear + "年" + mMonth + "月" + mDay + "日" + " " + "星期" + mWay + " " + mHour + ":" + mMinute + ":" + mSecond;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    goods = (Goods) data.getSerializableExtra("goods");
                }
            case 2:
                if (resultCode == RESULT_OK) {
                    buyer = (Buyer) data.getSerializableExtra("buyer");
                }
        }
    }

}
