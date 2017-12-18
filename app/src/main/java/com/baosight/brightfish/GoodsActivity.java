package com.baosight.brightfish;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baosight.brightfish.model.Checkin;
import com.baosight.brightfish.model.Checklist;
import com.baosight.brightfish.model.Checkout;
import com.baosight.brightfish.model.Goods;
import com.baosight.brightfish.util.CurrentTime;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class GoodsActivity extends BasicActivity implements View.OnClickListener{
    private TabLayout mTabLayout;
    ImageView photo;
    ImageView selectAblum;
    TextView sku, name,updateTime,goodsAmount,recentDateFIn,recentDateSIn,recentDateFOut,recentDateSOut,recentAmountFIn,recentAmountSIn,
            recentAmountFOut,recentAmountSOut;
    Button allCheckin,allCheckout;
    LinearLayout recentCheckin,recentCheckout;
    RelativeLayout recentCheckinS,recentCheckoutS;
    EditText  brand, catagory, size, color, spec, descr;
    View view1,view2,view3,view4;
    Goods goods;
    List<Checkin> checkinList;
    List<Checkout> checkoutList;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<View> mViewList = new ArrayList<>();//页卡视图集合

    public static void startGoodsActivity(Context context,Goods goods){
        Intent intent=new Intent(context,GoodsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goods", goods);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        goods=(Goods)getIntent().getBundleExtra("bundle").getSerializable("goods");
        checkinList=  DataSupport.where("goodsId="+goods.getId()).find(Checkin.class);
        checkoutList= DataSupport.where("goodsId="+goods.getId()).find(Checkout.class);
        initTab();
        initControlsV1();
        showGoods();
        initControlsV2();
        initToolbar(R.color.colorOrange);



    }
    @SuppressLint("InflateParams")
    private void initTab(){

        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.toolbar_tab);

        LayoutInflater mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.frag_tab_goods, null);
        view2 = mInflater.inflate(R.layout.frag_tab_activity_goods, null);
        view3 = mInflater.inflate(R.layout.frag_tab_goods, null);
        view4 = mInflater.inflate(R.layout.frag_tab_goods, null);

        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);

        //添加页卡标题
        mTitleList.add("基本参数");
        mTitleList.add("最近活动");
        mTitleList.add("商家分析");
        mTitleList.add("数据分析");

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));

        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        assert mViewPager != null;
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器

    }

    /**
     *
     * 货品基本参数初始化
     */
    private void initControlsV1(){
        photo = (ImageView) view1.findViewById(R.id.photo);
        selectAblum = (ImageView) view1.findViewById(R.id.select_ablum_btn);
        assert selectAblum != null;
        selectAblum.setOnClickListener(this);
        sku = (TextView) view1.findViewById(R.id.goods_sku);
        name = (TextView) view1.findViewById(R.id.goods_name);
        brand = (EditText) view1.findViewById(R.id.goods_brand);
        catagory = (EditText) view1.findViewById(R.id.goods_cata);
        size = (EditText) view1.findViewById(R.id.goods_size);
        color = (EditText) view1.findViewById(R.id.goods_color);
        spec = (EditText) view1.findViewById(R.id.goods_spec);
        descr = (EditText) view1.findViewById(R.id.goods_descr);
    }

    /**
     * 最近活动
     */
    private void initControlsV2(){
        updateTime=(TextView) view2.findViewById(R.id.update_time);
        goodsAmount=(TextView) view2.findViewById(R.id.goods_amount);
        recentAmountFIn=(TextView) view2.findViewById(R.id.amount_recent_first_in);
        recentAmountFOut=(TextView) view2.findViewById(R.id.amount_recent_first_out);
        recentAmountSIn=(TextView) view2.findViewById(R.id.amount_recent_second_in);
        recentAmountSOut=(TextView) view2.findViewById(R.id.amount_recent_second_out);
        recentDateFIn=(TextView) view2.findViewById(R.id.date_recent_first_in);
        recentDateFOut=(TextView) view2.findViewById(R.id.date_recent_first_out);
        recentDateSIn=(TextView) view2.findViewById(R.id.date_recent_second_in);
        recentDateSOut=(TextView) view2.findViewById(R.id.date_recent_second_out);
        recentCheckin=(LinearLayout) view2.findViewById(R.id.recent_checkin);
        recentCheckout=(LinearLayout) view2.findViewById(R.id.recent_checkout);
        recentCheckinS=(RelativeLayout) view2.findViewById(R.id.recent_checkin_s);
        recentCheckoutS=(RelativeLayout) view2.findViewById(R.id.recent_checkout_s);
        allCheckin=(Button) view2.findViewById(R.id.all_checkin);
        allCheckout=(Button) view2.findViewById(R.id.all_checkout);
        allCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecentCheckinActivity.startRecentCheckinActivity(GoodsActivity.this,checkinList);
            }
        });
        allCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecentCheckoutActivity.startRecentCheckoutActivity(GoodsActivity.this,checkoutList);
            }
        });
      showView2();

    }

    public void showView2(){
        updateTime.setText("更新时间： "+new CurrentTime().getHMTime());
        int checkinAmount=DataSupport.where("goodsId='"+goods.getId()+"'").sum(Checkin.class,"amount",int.class);
        int checkoutAmount=DataSupport.where("goodsId='"+goods.getId()+"'").sum(Checkout.class,"amount",int.class);
        goodsAmount.setText(checkinAmount-checkoutAmount+"");
        if(checkinList.size()<1){
            recentCheckin.setVisibility(View.GONE);
        }else if(checkinList.size()<2){
            Checkin fCheckin=checkinList.get(checkinList.size()-1);
            recentDateFIn.setText(fCheckin.getCheckinDate());
            recentAmountFIn.setText(fCheckin.getAmount()+"");
            recentCheckinS.setVisibility(View.GONE);
        }else {
            Checkin fCheckin=checkinList.get(checkinList.size()-1);
            recentDateFIn.setText(fCheckin.getCheckinDate());
            recentAmountFIn.setText(fCheckin.getAmount()+"");
            Checkin sCheckin=checkinList.get(checkinList.size()-2);
            recentDateSIn.setText(sCheckin.getCheckinDate());
            recentAmountSIn.setText(sCheckin.getAmount()+"");
        }
        if(checkoutList.size()<1){
            recentCheckout.setVisibility(View.GONE);
        }else if(checkoutList.size()<2){
            recentCheckoutS.setVisibility(View.GONE);
            Checkout fCheckout=checkoutList.get(checkoutList.size()-1);
            recentDateFOut.setText(fCheckout.getCheckinDate());
            recentAmountFOut.setText(fCheckout.getAmount()+"");
        }else {
            Checkout fCheckout=checkoutList.get(checkoutList.size()-1);
            recentDateFOut.setText(fCheckout.getCheckinDate());
            recentAmountFOut.setText(fCheckout.getAmount()+"");
            Checkout sCheckout=checkoutList.get(checkoutList.size()-2);
            recentDateSOut.setText(sCheckout.getCheckinDate());
            recentAmountSOut.setText(sCheckout.getAmount()+"");

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_ablum_btn:
                GoodsAblumActivity.startGoodsAblumActivity(GoodsActivity.this);
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }
    private void showGoods(){

        sku.setText(goods.getSku());
        name.setText(goods.getName());
        brand.setText(goods.getBrand());
        catagory.setText(goods.getCatagory());
        spec.setText(goods.getSpec());
        size.setText(goods.getSize());
        color.setText(goods.getColor());
        descr.setText(goods.getDescr());
        Bitmap bitmap = BitmapFactory.decodeFile(goods.getPhoto());
        photo.setImageBitmap(bitmap);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_mark:
               ModifyGoodsActivity.startModifyActivity(GoodsActivity.this,goods);
                break;
            case R.id.delete_btn:
                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setMessage("确定删除");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.delete(Goods.class,goods.getId());
                        DataSupport.deleteAll(Checklist.class,"goodsId=?",goods.getId()+"");
                        DataSupport.deleteAll(Checkin.class,"goodsId=?",goods.getId()+"");
                        DataSupport.deleteAll(Checkout.class,"goodsId=?",goods.getId()+"");
                        finish();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

}


