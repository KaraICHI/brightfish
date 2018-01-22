package com.baosight.brightfish.ui.goods;

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
import android.util.Log;
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

import com.baosight.brightfish.Dao.CheckoutDao;
import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.BuyerData;
import com.baosight.brightfish.domain.Checkin;
import com.baosight.brightfish.domain.Checkout;
import com.baosight.brightfish.domain.Goods;
import com.baosight.brightfish.domain.SupplierData;
import com.baosight.brightfish.ui.BasicActivity;
import com.baosight.brightfish.ui.buyer.ChooseBuyerActivity;
import com.baosight.brightfish.ui.checkin.RecentCheckinActivity;
import com.baosight.brightfish.ui.checkout.RecentCheckoutActivity;
import com.baosight.brightfish.ui.album.GoodsAblumActivity;
import com.baosight.brightfish.Dao.CheckinDao;
import com.baosight.brightfish.ui.search.choose.ChooseSupplierActivity;
import com.baosight.brightfish.util.CurrentTimeUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GoodsActivity extends BasicActivity implements View.OnClickListener {
    private static final String TAG = "GoodsActivity";
    private TabLayout mTabLayout;
    ImageView photo;
    ImageView selectAblum;
    TextView sku, name, updateTime, goodsAmount, recentDateFIn, recentDateSIn, recentDateFOut, recentDateSOut, recentAmountFIn, recentAmountSIn,
            recentAmountFOut, recentAmountSOut;
    Button allCheckin, allCheckout;
    LinearLayout recentCheckin, recentCheckout;
    RelativeLayout recentCheckinS, recentCheckoutS;
    EditText brand, catagory, size, spec, descr;
    TextView colorText;
    ImageView colorIcon;
    private TextView week;
    private TextView month;
    private TextView one_year;
    private TextView five_year;
    private TextView supplierCount;
    private TextView supplier_money;
    private TextView supplier_price;
    private TextView checkin_head;
    private TextView count_checkin;
    private TextView checkin_percent;
    private TextView supplier_all;
    private TextView buyer_count;
    private TextView buyer_money;
    private TextView buyer_price;
    private TextView checkout_head;
    private TextView count_checkout;
    private TextView checkout_percent;
    private TextView buyer_all;
    int[] colorIcons=new int[]{R.drawable.rect_color_blue,R.drawable.rect_color_orange,R.drawable.rect_color_green,
            R.drawable.rect_color_pur,R.drawable.rect_color_yellow,R.drawable.rect_color_lblue,R.drawable.rect_color_white,
            R.drawable.rect_color_gery,R.drawable.rect_color_black,R.drawable.rect_color_red,
            R.drawable.rect_color_brown};
    String[] colorTexts;
    View view1, view2, view3, view4;
    Goods goods;
    List<Checkin> checkinList;
    List<Checkout> checkoutList;
    Set<SupplierData> supplierDataSet;
    Set<BuyerData> buyerDataSet;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<View> mViewList = new ArrayList<>();//页卡视图集合

    public static void startGoodsActivity(Context context, Goods goods) {
        Intent intent = new Intent(context, GoodsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goods", goods);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        goods = (Goods) getIntent().getBundleExtra("bundle").getSerializable("goods");
        checkinList = DataSupport.where("goodsId=" + goods.getId()).find(Checkin.class);
        checkoutList = DataSupport.where("goodsId=" + goods.getId()).find(Checkout.class);
        supplierDataSet= CheckinDao.getSupplierDataByGoodsId(goods.getId());
        buyerDataSet= CheckoutDao.getBuyerDataByGoodsId(goods.getId());
        initTab();
        initControlsV1();
        showGoods();
        initControlsV2();
        initControl3();
        initToolbar(R.color.colorOrange);


    }

    @SuppressLint("InflateParams")
    private void initTab() {

        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.toolbar_tab);

        LayoutInflater mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.frag_tab_goods, null);
        view2 = mInflater.inflate(R.layout.frag_tab_activity_goods, null);
        view3 = mInflater.inflate(R.layout.frag_tab_buyer_analyze, null);
        view4 = mInflater.inflate(R.layout.frag_tab_buyer_analyze, null);

        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);//

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
     * 货品基本参数初始化
     */
    private void initControlsV1() {
        photo = (ImageView) view1.findViewById(R.id.photo);
        selectAblum = (ImageView) view1.findViewById(R.id.select_ablum_btn);
        assert selectAblum != null;
        selectAblum.setOnClickListener(this);
        sku = (TextView) view1.findViewById(R.id.goods_sku);
        name = (TextView) view1.findViewById(R.id.goods_name);
        brand = (EditText) view1.findViewById(R.id.goods_brand);
        catagory = (EditText) view1.findViewById(R.id.goods_cata);
        size = (EditText) view1.findViewById(R.id.goods_size);
        colorIcon= (ImageView) view1.findViewById(R.id.color_icon);
        colorText= (TextView) view1.findViewById(R.id.color_text);
        spec = (EditText) view1.findViewById(R.id.goods_spec);
        descr = (EditText) view1.findViewById(R.id.goods_descr);
    }

    /**
     * 最近活动
     */
    private void initControlsV2() {
        updateTime = (TextView) view2.findViewById(R.id.update_time);
        goodsAmount = (TextView) view2.findViewById(R.id.goods_amount);
        recentAmountFIn = (TextView) view2.findViewById(R.id.amount_recent_first_in);
        recentAmountFOut = (TextView) view2.findViewById(R.id.amount_recent_first_out);
        recentAmountSIn = (TextView) view2.findViewById(R.id.amount_recent_second_in);
        recentAmountSOut = (TextView) view2.findViewById(R.id.amount_recent_second_out);
        recentDateFIn = (TextView) view2.findViewById(R.id.date_recent_first_in);
        recentDateFOut = (TextView) view2.findViewById(R.id.date_recent_first_out);
        recentDateSIn = (TextView) view2.findViewById(R.id.date_recent_second_in);
        recentDateSOut = (TextView) view2.findViewById(R.id.date_recent_second_out);
        recentCheckin = (LinearLayout) view2.findViewById(R.id.recent_checkin);
        recentCheckout = (LinearLayout) view2.findViewById(R.id.recent_checkout);
        recentCheckinS = (RelativeLayout) view2.findViewById(R.id.recent_checkin_s);
        recentCheckoutS = (RelativeLayout) view2.findViewById(R.id.recent_checkout_s);
        allCheckin = (Button) view2.findViewById(R.id.all_checkin);
        allCheckout = (Button) view2.findViewById(R.id.all_checkout);
        allCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecentCheckinActivity.startRecentCheckinActivity(GoodsActivity.this, checkinList);
            }
        });
        allCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecentCheckoutActivity.startRecentCheckoutActivity(GoodsActivity.this, checkoutList);
            }
        });
        showView2();

    }
    void initControl3(){
        week = (TextView)view3.findViewById(R.id.week);
        Log.d(TAG, "initControl3: =============================="+week);
        month = (TextView)view3.findViewById(R.id.month);
        one_year = (TextView)view3.findViewById(R.id.one_year);
        five_year = (TextView)view3.findViewById(R.id.five_year);
        supplierCount = (TextView)view3.findViewById(R.id.supplierCount);
        supplier_money = (TextView)view3.findViewById(R.id.supplier_money);
        supplier_price = (TextView)view3.findViewById(R.id.supplier_price);
        checkin_head = (TextView)view3.findViewById(R.id.checkin_head);
        count_checkin = (TextView)view3.findViewById(R.id.count_checkin);
        checkin_percent = (TextView)view3.findViewById(R.id.checkin_percent);
        supplier_all = (TextView)view3.findViewById(R.id.supplier_all);

        buyer_count = (TextView)view3.findViewById(R.id.buyer_count);
        buyer_money = (TextView)view3.findViewById(R.id.buyer_money);
        buyer_price = (TextView)view3.findViewById(R.id.buyer_price);
        checkout_head = (TextView)view3.findViewById(R.id.checkout_head);
        count_checkout = (TextView)view3.findViewById(R.id.count_checkout);
        checkout_percent = (TextView)view3.findViewById(R.id.checkout_percent);
        buyer_all = (TextView)view3.findViewById(R.id.buyer_all);

    }

    public void showView2() {
        updateTime.setText("更新时间： " + CurrentTimeUtil.getHMTime());
        int checkinAmount = DataSupport.where("goodsId='" + goods.getId() + "'").sum(Checkin.class, "amount", int.class);
        int checkoutAmount = DataSupport.where("goodsId='" + goods.getId() + "'").sum(Checkout.class, "amount", int.class);
        goodsAmount.setText(checkinAmount - checkoutAmount + "");
        if (checkinList.size() < 1) {
            recentCheckin.setVisibility(View.GONE);
        } else if (checkinList.size() < 2) {
            Checkin fCheckin = checkinList.get(checkinList.size() - 1);
            recentDateFIn.setText(fCheckin.getCheckinDate());
            recentAmountFIn.setText(fCheckin.getAmount() + "");
            recentCheckinS.setVisibility(View.GONE);
        } else {
            Checkin fCheckin = checkinList.get(checkinList.size() - 1);
            recentDateFIn.setText(fCheckin.getCheckinDate());
            recentAmountFIn.setText(fCheckin.getAmount() + "");
            Checkin sCheckin = checkinList.get(checkinList.size() - 2);
            recentDateSIn.setText(sCheckin.getCheckinDate());
            recentAmountSIn.setText(sCheckin.getAmount() + "");
        }
        if (checkoutList.size() < 1) {
            recentCheckout.setVisibility(View.GONE);
        } else if (checkoutList.size() < 2) {
            recentCheckoutS.setVisibility(View.GONE);
            Checkout fCheckout = checkoutList.get(checkoutList.size() - 1);
            recentDateFOut.setText(fCheckout.getCheckinDate());
            recentAmountFOut.setText(fCheckout.getAmount() + "");
        } else {
            Checkout fCheckout = checkoutList.get(checkoutList.size() - 1);
            recentDateFOut.setText(fCheckout.getCheckinDate());
            recentAmountFOut.setText(fCheckout.getAmount() + "");
            Checkout sCheckout = checkoutList.get(checkoutList.size() - 2);
            recentDateSOut.setText(sCheckout.getCheckinDate());
            recentAmountSOut.setText(sCheckout.getAmount() + "");

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_ablum_btn:
                GoodsAblumActivity.startGoodsAblumActivity(GoodsActivity.this);
                break;
            case R.id.week:
                week.setTextColor(getResources().getColor(R.color.colorWhite));
                week.setBackgroundColor(getResources().getColor(R.color.colorHome));
                month.setTextColor(getResources().getColor(R.color.colorDarkGery));
                month.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                one_year.setTextColor(getResources().getColor(R.color.colorDarkGery));
                one_year.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                five_year.setTextColor(getResources().getColor(R.color.colorDarkGery));
                five_year.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                break;
            case R.id.month:
                week.setTextColor(getResources().getColor(R.color.colorDarkGery));
                week.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                month.setTextColor(getResources().getColor(R.color.colorWhite));
                month.setBackgroundColor(getResources().getColor(R.color.colorHome));
                one_year.setTextColor(getResources().getColor(R.color.colorDarkGery));
                one_year.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                five_year.setTextColor(getResources().getColor(R.color.colorDarkGery));
                five_year.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                break;
            case R.id.one_year:
                week.setTextColor(getResources().getColor(R.color.colorDarkGery));
                week.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                month.setTextColor(getResources().getColor(R.color.colorDarkGery));
                month.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                one_year.setTextColor(getResources().getColor(R.color.colorWhite));
                one_year.setBackgroundColor(getResources().getColor(R.color.colorHome));
                five_year.setTextColor(getResources().getColor(R.color.colorDarkGery));
                five_year.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                break;
            case R.id.five_year:
                week.setTextColor(getResources().getColor(R.color.colorDarkGery));
                week.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                month.setTextColor(getResources().getColor(R.color.colorDarkGery));
                month.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                one_year.setTextColor(getResources().getColor(R.color.colorDarkGery));
                one_year.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                five_year.setTextColor(getResources().getColor(R.color.colorWhite));
                five_year.setBackgroundColor(getResources().getColor(R.color.colorHome));
                break;
            case R.id.supplierCount:
                supplierCount.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                supplier_money.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                supplier_price.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                if (checkinList.size() == 0) {
                    checkin_head.setHeight(60);
                    count_checkin.setVisibility(View.GONE);
                    checkin_percent.setVisibility(View.GONE);
                    supplier_all.setVisibility(View.GONE);
                } else {
                    count_checkin.setVisibility(View.VISIBLE);
                    checkin_percent.setVisibility(View.VISIBLE);
                    supplier_all.setVisibility(View.VISIBLE);
                   // showSupplier();
                    for (Checkin checkin : checkinList) {
                        checkin_head.setText("1供货商/入库总量"+ CheckinDao.sumAllCheckinAmountByGoodsId(goods.getId()));
                        count_checkin.setText(CheckinDao.sumAllCheckinAmountByGoodsId(goods.getId())+"");
                    }
                }
                break;
            case R.id.supplier_money:
                supplierCount.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                supplier_money.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                supplier_price.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                if (checkinList.size() == 0) {
                    checkin_head.setHeight(60);
                    checkin_head.setText("0供货商/入库总额0￥");
                    count_checkin.setVisibility(View.GONE);
                    checkin_percent.setVisibility(View.GONE);
                    supplier_all.setVisibility(View.GONE);
                } else {
                    count_checkin.setVisibility(View.VISIBLE);
                    checkin_percent.setVisibility(View.VISIBLE);
                    supplier_all.setVisibility(View.VISIBLE);
                    checkin_head.setText("1供货商/入库总额"+ CheckinDao.sumAllCheckinMoneyByGoodsId(goods.getId())+"￥");
/*
                    for (Checkin checkin : checkinList) {
                        count_checkin.setText(checkin.getPrice());
                    }*/

                }
                break;
            case R.id.supplier_price:
                supplierCount.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                supplier_money.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                supplier_price.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                if (checkinList.size() == 0) {
                    checkin_head.setHeight(60);
                    checkin_head.setText("0供货商");
                    count_checkin.setVisibility(View.GONE);
                    checkin_percent.setVisibility(View.GONE);
                    supplier_all.setVisibility(View.GONE);
                } else {
                    count_checkin.setVisibility(View.VISIBLE);
                    checkin_percent.setVisibility(View.VISIBLE);
                    supplier_all.setVisibility(View.VISIBLE);
                    for (SupplierData supplierData : supplierDataSet) {
                        checkin_head.setText("1供货商");
                        count_checkin.setText(supplierData.getPriceAvg()+"");
                    }
                }
                break;
            case R.id.buyer_count:
                buyer_count.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                buyer_money.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buyer_price.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                if (checkoutList.size() == 0) {
                    checkout_head.setHeight(60);
                    count_checkout.setVisibility(View.GONE);
                    checkout_percent.setVisibility(View.GONE);
                    buyer_all.setVisibility(View.GONE);
                } else {
                    count_checkout.setVisibility(View.VISIBLE);
                    checkout_percent.setVisibility(View.VISIBLE);
                    buyer_all.setVisibility(View.VISIBLE);
                    checkout_head.setText("1买家/出库总量"+CheckoutDao.sumAllCheckoutAmountByGoodsId(goods.getId()));
                    for (BuyerData buyerData : buyerDataSet) {
                        count_checkout.setText(buyerData.getAmount()+"");
                    }
                }
                break;
            case R.id.buyer_money:
                buyer_count.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buyer_money.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                buyer_price.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                if (checkoutList.size() == 0) {
                    checkout_head.setHeight(60);
                    checkout_head.setText("0买家/出库总额0￥");
                    count_checkout.setVisibility(View.GONE);
                    checkout_percent.setVisibility(View.GONE);
                    buyer_all.setVisibility(View.GONE);
                } else {
                    count_checkout.setVisibility(View.VISIBLE);
                    checkout_percent.setVisibility(View.VISIBLE);
                    buyer_all.setVisibility(View.VISIBLE);
                    checkout_head.setText("1买家/出库总额"+CheckoutDao.sumAllCheckoutMoneyByGoodsId(goods.getId())+"￥");
                    for (BuyerData buyerData : buyerDataSet) {
                        count_checkout.setText(buyerData.getPriceSum()+"");
                    }

                }
                break;
            case R.id.buyer_price:
                buyer_count.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buyer_money.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buyer_price.setBackgroundColor(getResources().getColor(R.color.colorLightGery));
                if (checkoutList.size() == 0) {
                    checkout_head.setHeight(60);
                    checkout_head.setText("0供货商");
                    count_checkout.setVisibility(View.GONE);
                    checkout_percent.setVisibility(View.GONE);
                    buyer_all.setVisibility(View.GONE);
                } else {
                    count_checkout.setVisibility(View.VISIBLE);
                    checkout_percent.setVisibility(View.VISIBLE);
                    buyer_all.setVisibility(View.VISIBLE);
                    for (BuyerData buyerData : buyerDataSet) {
                        checkout_head.setText("1买家");
                        count_checkout.setText(buyerData.getPriceAvg()+"");
                    }
                }
                break;
            case R.id.supplier_all:
                startActivity(new Intent(this, ChooseSupplierActivity.class));
                break;
            case R.id.buyer_all:
                startActivity(new Intent(this, ChooseBuyerActivity.class));
                break;
            default:break;
        }
    }

    private void showSupplier() {

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

    private void showGoods() {
        colorTexts=getResources().getStringArray(R.array.color_text_list);
        sku.setText(goods.getSku());
        name.setText(goods.getName());
        brand.setText(goods.getBrand());
        catagory.setText(goods.getCatagory());
        spec.setText(goods.getSpec());
        size.setText(goods.getSize());
        if(goods.getColor()>=0){
            colorIcon.setImageResource(colorIcons[goods.getColor()]);
            colorText.setText(colorTexts[goods.getColor()]);
        }
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
            //    ModifyGoodsActivity.startModifyActivity(GoodsActivity.this, goods);
                Intent intent=new Intent(this,ModifyGoodsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("goods",goods);
                intent.putExtra("bundle",bundle);
                startActivityForResult(intent,1);
                break;
            case R.id.delete_btn:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("确定删除");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteGoods(goods.getId());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    goods=(Goods)data.getBundleExtra("bundle").getSerializable("goods");
                    showGoods();
                }
        }
    }
}


