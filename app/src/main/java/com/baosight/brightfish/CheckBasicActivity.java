package com.baosight.brightfish;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baosight.brightfish.model.Goods;
import com.baosight.brightfish.ui.ChooseGoodsdDialogAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.baosight.brightfish.CheckinActivity.goods;

/**
 * Created by Administrator on 2017/12/6.
 */

public class CheckBasicActivity extends BasicActivity implements View.OnClickListener{
    protected static final int msgKey1 = 1;
    protected String content = "";
    protected boolean isFocus;
    Toolbar toolbar;
    TextView currentTime;
    Button commit;
    static EditText goodsSku;
    static EditText goodsName;
    EditText price;
    EditText amount;
    EditText description;
    Dialog dialog;
    ImageButton goodsMenu, goodsRefesh, goodsPhoto;
    List<EditText> editTexts = new ArrayList<>();


    protected void initToolbar(int color) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setBackgroundColor(getResources().getColor(color));
    }

    protected void initControls() {

        currentTime = (TextView) findViewById(R.id.current_time);
        new CheckinActivity.TimeThread().start();
        commit = (Button) findViewById(R.id.checkin_commit);
        assert commit != null;
        goodsSku = (EditText) findViewById(R.id.goods_sku_checkin);
        goodsName = (EditText) findViewById(R.id.goods_name_checkin);
        price = (EditText) findViewById(R.id.price_checkin);
        amount = (EditText) findViewById(R.id.amount_checkin);
        description = (EditText) findViewById(R.id.descripe_checkin);
        goodsMenu = (ImageButton) findViewById(R.id.check_good_menu);
        goodsRefesh = (ImageButton) findViewById(R.id.check_good_refesh);
        goodsPhoto = (ImageButton) findViewById(R.id.photo_goods);

    }


    protected void setCheckLisener(){

        editTexts.add(goodsSku);
        editTexts.add(price);
        editTexts.add(amount);
        editTexts.add(description);
        editTexts.add(goodsName);
        commit.setOnClickListener(this);
        goodsPhoto.setOnClickListener(this);
        goodsMenu.setOnClickListener(this);
        goodsRefesh.setOnClickListener(this);

        for (EditText editText : editTexts) {
            editText.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

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



    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkin_bar_menu, menu);
        return true;
    }
    protected boolean isEditTextBlank() {

        if (goods == null && TextUtils.isEmpty(goodsSku.getText())) {
            showAlertDialog("内容不能为空");
            return true;
        } else if (!selectGoods(goodsSku.getText().toString())) {
            showNoThatGoodsDialog();
            return true;
        } else if (TextUtils.isEmpty(price.getText())) {
            showAlertDialog("价格不能为空");
            return true;
        } else if (TextUtils.isEmpty(amount.getText())) {
            showAlertDialog("数量不能为空");
            return true;
        }else {
            return false;
        }
    }
    protected void showNoThatGoodsDialog() {
        dialog = new Dialog(CheckBasicActivity.this);
        dialog.setTitle("没有该货品！");
        dialog.setContentView(R.layout.dialog_no_goods);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.dialog_rec);
        Button addNew=(Button) dialog.findViewById(R.id.add_new_goods);
        Button addCancel=(Button) dialog.findViewById(R.id.add_cancel);
        addCancel.setOnClickListener(this);
        addNew.setOnClickListener(this);
        ChooseGoodsdDialogAdapter adapter = new ChooseGoodsdDialogAdapter(DataSupport.findAll(Goods.class),dialog);
        LinearLayoutManager layoutManager=new LinearLayoutManager(CheckBasicActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        dialog.show();

    }



    protected void showAlertDialog(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(CheckBasicActivity.this);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setMessage(message);
        dialog.show();

    }
    protected Boolean selectGoods(String sku) {
        List<Goods> goodsList = DataSupport.where("sku = ?", sku).find(Goods.class);
        if (goodsList.size() >= 1) {
            goods = goodsList.get(0);
            return true;
        } else {

            return false;
        }
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
    public void clearEditText(){
        goodsName.setText("");
        goodsSku.setText("");
        price.setText("");
        amount.setText("");
        description.setText("");
    }




}
